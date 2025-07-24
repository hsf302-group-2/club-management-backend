package com.hsf302_group2.club_management_system.user.repository;

import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.user.dto.response.UserProfileResponse;
import com.hsf302_group2.club_management_system.user.dto.response.UserResponse;
import com.hsf302_group2.club_management_system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'CLUB_MEMBER' AND DATE(u.createdAt) BETWEEN :startDate AND :endDate")
    long countClubMembers(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'CLUB_MEMBER' ")
    long countTotalClubMembers();

    @Query("""
    SELECT new com.hsf302_group2.club_management_system.user.dto.response.UserProfileResponse (
        pm.id AS preMemberId, cm.id AS clubMemberId, u.id AS userId, u.email, u.fullName, u.status, pm.gender, pm.address, pm.phoneNumber, pm.dob
    )
    FROM User u
    JOIN u.preMember pm
    LEFT JOIN pm.clubMember cm
    WHERE u.email = :email
""")
    Optional<UserProfileResponse> findUserByEmail(@Param("email") String email);

}
