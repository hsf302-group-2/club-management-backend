package com.hsf302_group2.club_management_system.clubevent.repository;

import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubEventRepository extends JpaRepository<ClubEvent, Integer> {

    @Query("SELECT e FROM ClubEvent e JOIN e.eventRegistrations er WHERE er.preMember.id = :preMemberId")
    List<ClubEvent>findByUserRegistrations(@Param("preMemberId") String preMemberId);


}
