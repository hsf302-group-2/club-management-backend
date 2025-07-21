package com.hsf302_group2.club_management_system.activityregistration.repository;

import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRegistrationRepository extends JpaRepository<ActivityRegistration, Integer> {
    boolean existsByClubActivityIdAndClubMemberId(int clubActivityId, String clubMemberId);
}
