package com.hsf302_group2.club_management_system.clubpoint.service;

import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import com.hsf302_group2.club_management_system.clubmember.repository.ClubMemberRepository;
import com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointHistoryResponse;
import com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointTotalResponse;
import com.hsf302_group2.club_management_system.clubpoint.repository.ClubPointRepository;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClubPointService {
    ClubPointRepository clubPointRepository;
    ClubMemberRepository clubMemberRepository;

    public List<ClubPointTotalResponse> getAllClubMemberPoints(){
        return clubPointRepository.findAllClubMemberTotalPoints();
    }

    public List<ClubPointTotalResponse> getTopClubMemberPoints(){
        Pageable pageable = PageRequest.of(0, 3);
        return clubPointRepository.findTopClubMembers(pageable);
    }

    public ClubPointTotalResponse getClubMemberPointById(String clubMemberId){
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_MEMBER_NOT_EXISTED));
        return clubPointRepository.getTotalPointByClubMemberId(clubMember.getId()).
                orElseThrow(() -> new AppException(ErrorCode.CLUB_MEMBER_NOT_EXISTED));
    }

    public List<ClubPointHistoryResponse> getHistoryPoint(String clubMemberId){
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_MEMBER_NOT_EXISTED));
        return clubPointRepository.getPointHistoryResponseByClubMemberId(clubMember.getId());
    }

}
