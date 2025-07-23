package com.hsf302_group2.club_management_system.clubpoint.repository;

import com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointHistoryResponse;
import com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointTotalResponse;
import com.hsf302_group2.club_management_system.clubpoint.entity.ClubPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubPointRepository extends JpaRepository<ClubPoint, Integer> {
    @Query("SELECT new com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointTotalResponse(cm.id, u.fullName, CAST(COALESCE(SUM(cp.point), 0) AS int)) FROM ClubMember cm JOIN cm.preMember pm JOIN pm.user u LEFT JOIN ClubPoint cp ON cp.clubMember = cm GROUP BY cm.id, u.fullName ORDER BY SUM(cp.point) DESC")
    List<ClubPointTotalResponse> findAllClubMemberTotalPoints();

    @Query("SELECT new com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointTotalResponse(cm.id, u.fullName, CAST(COALESCE(SUM(cp.point), 0) AS int)) FROM ClubMember cm JOIN cm.preMember pm JOIN pm.user u LEFT JOIN ClubPoint cp ON cp.clubMember = cm GROUP BY cm.id, u.fullName ORDER BY SUM(cp.point) DESC")
    List<ClubPointTotalResponse> findTopClubMembers(Pageable pageable);

    @Query("SELECT new com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointTotalResponse(cm.id, u.fullName, CAST(COALESCE(SUM(cp.point), 0) AS int)) FROM ClubMember cm JOIN cm.preMember pm JOIN pm.user u LEFT JOIN ClubPoint cp ON cp.clubMember = cm WHERE cm.id = :clubMemberId GROUP BY cm.id, u.fullName")
    Optional<ClubPointTotalResponse> getTotalPointByClubMemberId(@Param("clubMemberId") String clubMemberId);

    @Query("SELECT new com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointHistoryResponse(cp.id, cp.point, cp.reason, cp.calculatedAt) FROM ClubPoint cp WHERE cp.clubMember.id = :clubMemberId ORDER BY cp.calculatedAt DESC")
    List<ClubPointHistoryResponse> getPointHistoryResponseByClubMemberId(@Param("clubMemberId") String clubMemberId);

}
