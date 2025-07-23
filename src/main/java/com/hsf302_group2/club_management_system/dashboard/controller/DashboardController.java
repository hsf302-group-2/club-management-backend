package com.hsf302_group2.club_management_system.dashboard.controller;

import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.dashboard.dto.response.StatsResponse;
import com.hsf302_group2.club_management_system.dashboard.dto.response.WeeklyStatsResponse;
import com.hsf302_group2.club_management_system.dashboard.service.DashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardController {
    DashboardService dashboardService;

    @GetMapping("/stats")
    public ApiResponse<List<StatsResponse>> getStats(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate ){
        return ApiResponse.<List<StatsResponse>>builder()
                .success(true)
                .data(dashboardService.getAllStats(startDate, endDate))
                .build();

    }

    @GetMapping("/stats/weekly")
    public ApiResponse <List<WeeklyStatsResponse>> getStatsWeekly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate ){
        return ApiResponse.<List<WeeklyStatsResponse>>builder()
                .success(true)
                .data(dashboardService.getAllStatsWeekly(startDate, endDate))
                .build();

    }
}
