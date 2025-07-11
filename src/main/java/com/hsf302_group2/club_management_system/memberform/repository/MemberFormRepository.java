package com.hsf302_group2.club_management_system.memberform.repository;

import com.hsf302_group2.club_management_system.memberform.entity.MemberForm;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberFormRepository extends JpaRepository<MemberForm, Integer> {
    @Query("SELECT m FROM MemberForm m WHERE m.preMember = :preMember")
    List<MemberForm> findByPreMember(@Param("preMember") PreMember preMember);

}
