package com.hsf302_group2.club_management_system.security.service;

import com.hsf302_group2.club_management_system.common.enums.UserStatus;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.PreMemberMapper;
import com.hsf302_group2.club_management_system.common.mapper.UserMapper;
import com.hsf302_group2.club_management_system.mail.MailService;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import com.hsf302_group2.club_management_system.premember.repository.PreMemberRepository;
import com.hsf302_group2.club_management_system.premember.service.PreMemberService;
import com.hsf302_group2.club_management_system.security.dto.request.AuthenticationRequest;
import com.hsf302_group2.club_management_system.security.dto.request.IntrospectRequest;
import com.hsf302_group2.club_management_system.security.dto.request.RefreshRequest;
import com.hsf302_group2.club_management_system.security.dto.response.AuthenticationResponse;
import com.hsf302_group2.club_management_system.security.dto.response.IntrospectResponse;
import com.hsf302_group2.club_management_system.security.dto.response.RefreshResponse;
import com.hsf302_group2.club_management_system.user.dto.request.UserCreationRequest;
import com.hsf302_group2.club_management_system.user.dto.response.UserResponse;
import com.hsf302_group2.club_management_system.user.entity.User;
import com.hsf302_group2.club_management_system.user.repository.UserRepository;
import com.hsf302_group2.club_management_system.user.service.UserService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class AuthenticationService {
    UserService userService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    PreMemberRepository preMemberRepository;
    MailService mailService;
    PreMemberMapper preMemberMapper;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;

    public PreMemberResponse createPreMemberAccount (UserCreationRequest request){
        if (userService.isEmailExisted(request.getEmail())) {
            throw new RuntimeException(ErrorCode.EMAIL_EXISTED.getMessage());
        }

        User user = userMapper.toUser(request);
        user.setRole("PRE_MEMBER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.PENDING.name());
        PreMember preMember = new PreMember();
        preMember.setUser(user);
        PreMember savedPreMember = preMemberRepository.save(preMember);

        String token = generateEmailVerificationToken(user);
        String link = "http://localhost:5173/api/auth/verify?token=" + token;
        mailService.sendVerificationEmail(user.getEmail(), link);
        return preMemberMapper.toPreMemberResponse(savedPreMember);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userService.findUserByEmail(request.getEmail());
        if (UserStatus.PENDING.name().equals(user.getStatus())) {
            throw new AppException(ErrorCode.USER_NOT_VERIFIED);
        }
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var accessToken = generateToken(user);
        var refreshToken = generateRefreshToken(user);
        AuthenticationResponse authen = AuthenticationResponse
                .builder()
                .user(userMapper.toUserResponse(user))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
        return authen;
    }

    public String verifyEmailToken(String token) {
        try {
            SignedJWT signedJWT = verifyToken(token);

            String type = (String) signedJWT.getJWTClaimsSet().getClaim("type");
            if (!"EMAIL_VERIFICATION".equals(type)) {
                throw new AppException(ErrorCode.INVALID_TOKEN_TYPE);
            }

            String userId = (String) signedJWT.getJWTClaimsSet().getClaim("id");
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            if (UserStatus.ACTIVE.name().equals(user.getStatus())) {
                throw new AppException(ErrorCode.USER_ALREADY_VERIFIED);
            }

            user.setStatus(UserStatus.ACTIVE.name());
            userRepository.save(user);
            return "Email verified successfully";

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }


    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                .Builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", "ROLE_" + user.getRole())
                .build();
        Payload payload =  new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try{
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
        }catch (JOSEException e){
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    private String generateRefreshToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", "REFRESH_TOKEN")
                .build();

        JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));

        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException("Cannot create token", e);
        }

        return jwsObject.serialize();
    }

    public String generateEmailVerificationToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("type", "EMAIL_VERIFICATION")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .jwtID(UUID.randomUUID().toString())
                .build();

        JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));

        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException("Cannot create token", e);
        }

        return jwsObject.serialize();
    }



    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if(!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getRefreshToken());
        var email = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var accessToken = generateToken(user);
        return RefreshResponse.builder().accessToken(accessToken).build();
    }

}
