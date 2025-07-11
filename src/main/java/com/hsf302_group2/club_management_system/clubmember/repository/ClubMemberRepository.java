package com.hsf302_group2.club_management_system.clubmember.repository;

import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
}
