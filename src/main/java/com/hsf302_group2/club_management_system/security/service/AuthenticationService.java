package com.hsf302_group2.club_management_system.security.service;

import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.UserMapper;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
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
    PreMemberService preMemberService;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;

    public PreMemberResponse createPreMemberAccount (UserCreationRequest request, String role){
        User user = userRepository.getUserByEmail(request.getEmail());
        if(user != null){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        return preMemberService.createPreMember(request, role);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userService.findUserByEmail(request.getEmail());
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
                .issuer("medcarehiv.com")
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
                .issuer("medcarehiv.com")
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
