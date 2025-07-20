package com.hsf302_group2.club_management_system.premember.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import com.hsf302_group2.club_management_system.memberform.entity.MemberForm;
import com.hsf302_group2.club_management_system.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PreMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String gender;

    LocalDate dob;

    String address;

    String phoneNumber;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference("user-preMember")
    User user;

    @OneToMany(mappedBy = "preMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<MemberForm> memberForms;

    @OneToOne(mappedBy = "preMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    ClubMember clubMember;
}
