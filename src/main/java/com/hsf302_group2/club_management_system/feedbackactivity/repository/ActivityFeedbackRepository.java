package com.hsf302_group2.club_management_system.feedbackactivity.repository;

import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.feedbackactivity.dto.response.ActivityFeedbackResponse;
import com.hsf302_group2.club_management_system.feedbackactivity.entity.ActivityFeedback;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.FeedbackFormActivityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityFeedbackRepository extends JpaRepository<ActivityFeedback,Integer> {
    boolean existsFeedbackFormByActivityRegistration(ActivityRegistration activityRegistration);

    @Query("SELECT new com.hsf302_group2.club_management_system.feedbackactivity.dto.response.ActivityFeedbackResponse(af.id, ca.id, cm.id, ar.id, u.fullName, af.rating, af.content) FROM ActivityFeedback af JOIN af.activityRegistration ar JOIN ar.clubActivity ca JOIN ar.clubMember cm JOIN cm.preMember pm JOIN pm.user u WHERE ca.id = :clubActivityId")
    List<ActivityFeedbackResponse> findFeedbackFormByClubActivity(@Param("clubActivityId") int clubActivityId);
}
