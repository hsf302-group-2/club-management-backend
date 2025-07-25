package com.hsf302_group2.club_management_system.clubmember.service;

import com.hsf302_group2.club_management_system.clubmember.dto.response.ClubMemberResponse;
import com.hsf302_group2.club_management_system.clubmember.repository.ClubMemberRepository;
import com.hsf302_group2.club_management_system.common.mapper.ClubMemberMapper;
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
public class ClubMemberService {
    ClubMemberRepository clubMemberRepository;
    ClubMemberMapper clubMemberMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public List<ClubMemberResponse> getAllClubMembers() {
        return clubMemberRepository.findAll().stream()
                .map(clubMemberMapper::toClubMemberResponse)
                .collect(Collectors.toList());
    }
}
