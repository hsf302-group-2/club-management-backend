package com.hsf302_group2.club_management_system.clubevent.service;

import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventCreationRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventUpdateRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.response.ClubEventResponse;
import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import com.hsf302_group2.club_management_system.clubevent.repository.ClubEventRepository;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.ClubEventMapper;
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
public class ClubEventService {
    ClubEventRepository clubEventRepository;
    ClubEventMapper clubEventMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public ClubEventResponse createClubEvent(ClubEventCreationRequest request){
        ClubEvent clubEvent = clubEventMapper.toClubEvent(request);
        return clubEventMapper.toClubEventResponse(clubEventRepository.save(clubEvent));

    }

    @PreAuthorize("hasRole('ADMIN')")
    public ClubEventResponse getClubEvent(int clubEventId) {
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_EVENT_NOT_EXISTED));
        return clubEventMapper.toClubEventResponse(clubEvent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ClubEventResponse> getAllClubEvents() {
        return clubEventRepository.findAll().stream()
                .map(clubEventMapper::toClubEventResponse)
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('ADMIN')")
    public ClubEventResponse updateClubEvent(int clubEventId, ClubEventUpdateRequest request){
        ClubEvent clubEvent = clubEventRepository.findById(clubEventId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_EVENT_NOT_EXISTED));
        clubEventMapper.updateClubEvent(clubEvent, request);
        return clubEventMapper.toClubEventResponse(clubEventRepository.save(clubEvent));

    }



}
