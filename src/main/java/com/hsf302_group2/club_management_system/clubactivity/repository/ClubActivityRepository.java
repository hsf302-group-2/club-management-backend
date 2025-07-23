package com.hsf302_group2.club_management_system.clubactivity.repository;

import com.hsf302_group2.club_management_system.clubactivity.entity.ClubActivity;
import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubActivityRepository extends JpaRepository<ClubActivity, Integer> {

}
