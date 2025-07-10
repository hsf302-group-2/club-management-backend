package com.hsf302_group2.club_management_system.premember.repository;

import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreMemberRepository extends JpaRepository<PreMember, String> {
    @Query("""
        SELECT new com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse (
            p.id, u.id, u.email, u.fullName, u.status, p.gender, p.address, p.phoneNumber, p.dob
            )
        FROM PreMember p JOIN p.user u WHERE u.email = :email""")
    Optional<PreMemberResponse> findPreMemberByToken(@Param("email") String email);



}
