package org.sparta.jenview.statistics.controller;

import org.hibernate.annotations.processing.SQL;
import org.sparta.jenview.statistics.dto.VideoStatDTO;
import org.sparta.jenview.statistics.service.VideoStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class VideoStatController {

    private final VideoStatService videoStatService;

    @Autowired
    public VideoStatController(VideoStatService videoStatService) {
        this.videoStatService = videoStatService;
    }

    @GetMapping("/top5/views")
    public List<VideoStatDTO> getTop5VideosByViews(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("period") String period) {
        return videoStatService.getTop5VideosByViews(date, period);
    }

    @GetMapping("/top5/playtime")
    public List<VideoStatDTO> getTop5VideosByPlayTime(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("period") String period) {
        return videoStatService.getTop5VideosByPlayTime(date, period);
    }

    @PostMapping
    public String generateAllVideoStats() {
        videoStatService.createStatisticsForAllVideos();
        return "모든 비디오에 대한 통계 데이터가 생성되었습니다.";
    }
}

