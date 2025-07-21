package com.hsf302_group2.club_management_system.clubmember.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    LocalDateTime joinedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_member_id", nullable = false, unique = true)
    PreMember preMember;

    @OneToMany(mappedBy = "clubMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("clubMember-eventRegistrations")
    List<ActivityRegistration> activityRegistrations;
}
