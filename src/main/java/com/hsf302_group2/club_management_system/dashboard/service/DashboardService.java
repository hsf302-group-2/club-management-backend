package com.hsf302_group2.club_management_system.dashboard.service;

import com.hsf302_group2.club_management_system.clubactivity.repository.ClubActivityRepository;
import com.hsf302_group2.club_management_system.clubevent.repository.ClubEventRepository;
import com.hsf302_group2.club_management_system.dashboard.dto.response.StatsResponse;
import com.hsf302_group2.club_management_system.dashboard.dto.response.WeeklyStatsResponse;
import com.hsf302_group2.club_management_system.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardService {
    UserRepository userRepository;
    ClubActivityRepository clubActivityRepository;
    ClubEventRepository clubEventRepository;

    private StatsResponse formatStats(String title, long currentValue, long previousValue){
        String value = String.format("%,d", currentValue);

        double percentValue;
        if(previousValue == 0){
            percentValue = (currentValue ==0) ? 0 :100;
        }
        else{
            percentValue = ((double) (currentValue-previousValue) / previousValue) *100;
        }

        String change = (percentValue >= 0 ? "+" : "") + String.format("%.0f", percentValue)  + "%";
        boolean isGrowing = percentValue > 0;
        return new StatsResponse(title, value, change, isGrowing);
    }

    public StatsResponse getTotalClubMember(LocalDate startDate, LocalDate endDate){
        long previousValue = userRepository.countClubMembers(startDate, endDate);
        long currentValue = userRepository.countTotalClubMembers();
        return formatStats("Thành viên CLB ", currentValue, previousValue);
    }

    public StatsResponse getTotalClubEvent(LocalDate startDate, LocalDate endDate){
        long previousValue = clubEventRepository.countClubEvents(startDate, endDate);
        long currentValue = clubEventRepository.countTotalClubEvents();
        return formatStats("Sự kiện CLB", currentValue, previousValue);
    }

    public StatsResponse getTotalClubActivity(LocalDate startDate, LocalDate endDate){
        long previousValue = clubActivityRepository.countClubActivities(startDate, endDate);
        long currentValue = clubActivityRepository.countTotalClubActivities();
        return formatStats("Hoạt động CLB", currentValue, previousValue);
    }

    public List<StatsResponse> getAllStats(LocalDate startDate, LocalDate endDate){
        return List.of(
                getTotalClubMember(startDate, endDate),
                getTotalClubEvent(startDate, endDate),
                getTotalClubActivity(startDate, endDate)
        );
    }

    public List<WeeklyStatsResponse> getAllStatsWeekly(LocalDate startDate, LocalDate endDate ){
        List<WeeklyStatsResponse> weeklyStats = new ArrayList<>();
        long weeks = ChronoUnit.WEEKS.between(startDate, endDate) +1;
        LocalDate startWeek = startDate;

        long previousClubMember = 0;
        long previousClubEvent = 0;
        long previousClubActivity = 0;

        for(int i =0; i<weeks; i++){
            LocalDate endWeek = startWeek.plusDays(6);
            if(endWeek.isAfter(endDate)){
                endWeek = endDate;
            }

            long clubMemberCount = userRepository.countClubMembers(startWeek, endWeek);
            long clubEventCount = clubEventRepository.countClubEvents(startWeek, endWeek);
            long clubActivityCount = clubActivityRepository.countClubActivities(startWeek, endWeek);

            List<StatsResponse> stats = List.of(
                    formatStats("Thành viên CLB", clubMemberCount, previousClubMember),
                    formatStats("Sự kiện CLB", clubEventCount, previousClubEvent),
                    formatStats("Hoạt động CLB", clubActivityCount, previousClubActivity)
            );

            String weekRange = startWeek.toString() + " -> " + endWeek.toString();
            weeklyStats.add(new WeeklyStatsResponse(weekRange, stats));

            previousClubMember = clubMemberCount;
            previousClubEvent = clubEventCount;
            previousClubActivity = clubActivityCount;
            startWeek = startWeek.plusWeeks(1);
        }

        return weeklyStats;
    }
}
