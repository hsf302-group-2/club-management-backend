package com.hsf302_group2.club_management_system.user.repository;

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

}
