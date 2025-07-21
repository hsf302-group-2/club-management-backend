package com.hsf302_group2.club_management_system.clubactivity.service;

import com.hsf302_group2.club_management_system.clubactivity.dto.request.ClubActivityCreationRequest;
import com.hsf302_group2.club_management_system.clubactivity.dto.request.ClubActivityUpdateRequest;
import com.hsf302_group2.club_management_system.clubactivity.dto.response.ClubActivityResponse;
import com.hsf302_group2.club_management_system.clubactivity.entity.ClubActivity;
import com.hsf302_group2.club_management_system.clubactivity.repository.ClubActivityRepository;
import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventUpdateRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.response.ClubEventResponse;
import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.ClubActivityMapper;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import com.hsf302_group2.club_management_system.premember.service.PreMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClubActivityService {

    ClubActivityRepository clubActivityRepository;
    ClubActivityMapper clubActivityMapper;
    PreMemberService preMemberService;

    @PreAuthorize("hasRole('ADMIN')")
    public ClubActivityResponse createClubActivity(ClubActivityCreationRequest request){
        ClubActivity clubActivity = clubActivityMapper.toClubActivity(request);
        return clubActivityMapper.toClubActivityResponse(clubActivityRepository.save(clubActivity));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ClubActivityResponse getClubActivity(int clubActivityId) {
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_ACTIVITY_NOT_EXISTED));
        return clubActivityMapper.toClubActivityResponse(clubActivity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ClubActivityResponse> getAllClubActivities() {
        return clubActivityRepository.findAll().stream()
                .map(clubActivityMapper::toClubActivityResponse)
                .collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('ADMIN')")
    public ClubActivityResponse updateClubActivity(int clubActivityId, ClubActivityUpdateRequest request){
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_ACTIVITY_NOT_EXISTED));
        clubActivityMapper.updateClubActivity(clubActivity, request);
        return clubActivityMapper.toClubActivityResponse(clubActivityRepository.save(clubActivity));

    }

    @PreAuthorize("hasRole('CLUB_MEMBER')")
    public List<ClubActivityResponse> getRegisteredActivities(){
        PreMember preMember = preMemberService.getPreMemberResponseByToken();
        List<ClubActivity> activities = clubActivityRepository.findByUserActivityRegistrations(preMember.getClubMember().getId());

        if (activities.isEmpty()){
            throw new AppException(ErrorCode.NO_REGISTERED_ACTIVITIES);
        }

        return activities.stream()
                .map(clubActivityMapper::toClubActivityResponse)
                .collect(Collectors.toList());
    }
}
