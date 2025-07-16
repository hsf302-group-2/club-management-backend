package com.hsf302_group2.club_management_system.eventregistration.repository;

import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    boolean existsByClubEventIdAndUserId(int clubEventId, String userId);
}
