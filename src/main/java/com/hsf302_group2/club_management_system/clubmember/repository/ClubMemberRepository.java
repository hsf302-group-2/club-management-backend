package com.hsf302_group2.club_management_system.clubmember.repository;

import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

}
