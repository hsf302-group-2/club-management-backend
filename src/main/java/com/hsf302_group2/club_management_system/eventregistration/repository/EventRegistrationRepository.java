package com.hsf302_group2.club_management_system.eventregistration.repository;

import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import com.hsf302_group2.club_management_system.eventregistration.dto.response.EventRegistrationResponse;
import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    boolean existsByClubEventIdAndPreMemberId(int clubEventId, String preMemberId);

    @Query("SELECT er.clubEvent FROM EventRegistration er  WHERE er.preMember.id = : preMemberId")
    List<ClubEvent> findEventsByPreMemberId(@Param("preMemberId") String preMemberId);
}
