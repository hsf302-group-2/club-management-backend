package com.hsf302_group2.club_management_system.activityregistration.repository;

import com.hsf302_group2.club_management_system.activityregistration.dto.response.ActivityRegistrationResponse;
import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.clubactivity.entity.ClubActivity;
import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRegistrationRepository extends JpaRepository<ActivityRegistration, Integer> {
    boolean existsByClubActivityIdAndClubMemberId(int clubActivityId, String clubMemberId);

    Optional<ActivityRegistration> findActivityRegistrationByClubActivityIdAndClubMemberId(int clubActivityId, String clubMemberId);

    @Query("SELECT new com.hsf302_group2.club_management_system.activityregistration.dto.response.ActivityRegistrationResponse(ar.id, ar.clubActivity.id, cm.id, u.fullName,pm.gender, ar.registrationTime, ar.status) FROM ActivityRegistration ar JOIN ar.clubMember cm JOIN cm.preMember pm JOIN pm.user u WHERE ar.clubActivity.id = :clubActivityId")
    List<ActivityRegistrationResponse> findActivityRegistrationByClubActivityId(@Param("clubActivityId") int clubActivityId);

    @Query("SELECT ar FROM ActivityRegistration ar WHERE ar.clubMember.id = :clubMemberId")
    List<ActivityRegistration> findByUserActivityRegistrations(@Param("clubMemberId") String clubMemberId);
}
