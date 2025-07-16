package com.hsf302_group2.club_management_system.clubevent.repository;

import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubEventRepository extends JpaRepository<ClubEvent, Integer> {
}
