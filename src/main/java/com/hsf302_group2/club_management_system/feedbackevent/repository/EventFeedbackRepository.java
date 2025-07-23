package com.hsf302_group2.club_management_system.feedbackevent.repository;

import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.EventFeedbackResponse;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.FeedbackFormActivityResponse;
import com.hsf302_group2.club_management_system.feedbackevent.entity.EventFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventFeedbackRepository extends JpaRepository<EventFeedback,Integer> {
    boolean existsFeedbackFormByEventRegistration(EventRegistration eventRegistration);

    @Query("SELECT new com.hsf302_group2.club_management_system.feedbackevent.dto.response.EventFeedbackResponse(fb.id, ce.id, pm.id, er.id, u.fullName , fb.rating, fb.content) FROM EventFeedback fb JOIN fb.eventRegistration er JOIN er.clubEvent ce JOIN er.preMember pm JOIN pm.user u WHERE ce.id = :clubEventId")
    List<EventFeedbackResponse> findFeedbackFormByClubEvent(@Param("clubEventId") int clubEventId);


}
