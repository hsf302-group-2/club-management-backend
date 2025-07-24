package com.hsf302_group2.club_management_system.clubmember.repository;

import com.hsf302_group2.club_management_system.clubmember.dto.response.ClubMemberResponse;
import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
    @Query("""
        SELECT new com.hsf302_group2.club_management_system.clubmember.dto.response.ClubMemberResponse (
            cm.id, u.id, u.email, u.fullName, u.status, pm.gender, pm.address, pm.phoneNumber, pm.dob
            )
        FROM ClubMember cm JOIN cm.preMember pm JOIN pm.user u WHERE u.email = :email""")
    Optional<ClubMemberResponse> findClubMemberByEmail(@Param("email") String email);


}
