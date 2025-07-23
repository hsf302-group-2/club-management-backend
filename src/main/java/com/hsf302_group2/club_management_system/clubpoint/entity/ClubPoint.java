package com.hsf302_group2.club_management_system.clubpoint.entity;

import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    int point;

    @Column(nullable = false)
    String reason;

    @Column(nullable = false)
    LocalDateTime calculatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id", nullable = false)
    ClubMember clubMember;

    public ClubPoint(ClubMember clubMember, int point, String reason,  LocalDateTime calculatedAt ) {
        this.clubMember = clubMember;
        this.calculatedAt = calculatedAt;
        this.reason = reason;
        this.point = point;
    }
}
