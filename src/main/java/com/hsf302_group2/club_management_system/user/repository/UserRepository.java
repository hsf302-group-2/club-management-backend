package com.hsf302_group2.club_management_system.user.repository;

import com.hsf302_group2.club_management_system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);
    User getUserByEmail(String email);

}
