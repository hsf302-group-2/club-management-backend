package com.hsf302_group2.club_management_system.feedbackevent.entity;

import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class EventFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    @NotNull(message = "Rating is required !")
    int rating;

    @Column(nullable = false)
    @NotBlank(message = "Please leave some words !")
    String content;

    @Column(nullable = false)
    LocalDateTime submittedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_registration_id", nullable = false)
    EventRegistration eventRegistration;

}
