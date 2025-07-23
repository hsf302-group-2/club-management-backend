package com.hsf302_group2.club_management_system.activityregistration.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hsf302_group2.club_management_system.clubactivity.entity.ClubActivity;
import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import com.hsf302_group2.club_management_system.common.enums.RegistrationStatus;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ActivityRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    LocalDateTime registrationTime;

    @Enumerated(EnumType.STRING)
    RegistrationStatus status = RegistrationStatus.CONFIRMED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_activity_id", nullable = false)
    @JsonBackReference
    ClubActivity clubActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubmember_id", nullable = false)
    @JsonBackReference("clubMember-activityRegistrations")
    ClubMember clubMember;

}
