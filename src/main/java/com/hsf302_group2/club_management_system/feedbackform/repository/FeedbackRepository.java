package com.hsf302_group2.club_management_system.feedbackform.repository;

import com.hsf302_group2.club_management_system.feedbackform.entity.FeedbackForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackForm, Integer> {
}
