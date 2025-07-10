package com.hsf302_group2.club_management_system.memberform.entity;

import com.hsf302_group2.club_management_system.common.enums.FormStatus;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class MemberForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String reason;

    String skill;

    String achievement;

    String expectation;

    @Enumerated(EnumType.STRING)
    FormStatus status = FormStatus.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_member_id", nullable = false)
    PreMember preMember;
}
