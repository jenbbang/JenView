package org.sparta.jenview.statistics.controller;

import org.sparta.jenview.statistics.dto.VideoStatResponseDTO;
import org.sparta.jenview.statistics.dto.VideoStatDTO;
import org.sparta.jenview.statistics.service.VideoStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class VideoStatController {

    private final VideoStatService videoStatService;

    @Autowired
    public VideoStatController(VideoStatService videoStatService) {
        this.videoStatService = videoStatService;
    }

    @GetMapping("/top5/viewcount")
    public VideoStatResponseDTO getTop5ByViewCount(
            @RequestParam(name = "period", defaultValue = "daily") String period) {
        LocalDateTime start, end = LocalDate.now().atTime(LocalTime.MAX);
        String message = "";

        switch (period.toLowerCase()) {
            case "daily":
                message = "일일 통계가 완료되었습니다.";
                start = LocalDate.now().atStartOfDay();
                break;
            case "weekly":
                start = LocalDate.now().minus(1, ChronoUnit.WEEKS).atStartOfDay();
                message = "주간 통계가 완료되었습니다.";
                break;
            case "monthly":
                start = LocalDate.now().minus(1, ChronoUnit.MONTHS).atStartOfDay();
                message = "월간 통계가 완료되었습니다.";
                break;
            default:
                throw new IllegalArgumentException("잘못된 기간 입니다.");
        }

        List<VideoStatDTO> stats = videoStatService.getTop5ByViewCount(start, end);
        return new VideoStatResponseDTO(stats, message);
    }

    @GetMapping("/top5/playtime")
    public VideoStatResponseDTO getTop5ByTotalPlayTime(
            @RequestParam(name = "period", defaultValue = "daily") String period) {
        LocalDateTime start, end = LocalDate.now().atTime(LocalTime.MAX);
        String message = "";

        switch (period.toLowerCase()) {
            case "daily":
                start = LocalDate.now().atStartOfDay();
                message = "일일 통계가 완료되었습니다.";
                break;
            case "weekly":
                start = LocalDate.now().minus(1, ChronoUnit.WEEKS).atStartOfDay();
                message = "주간 통계가 완료되었습니다.";
                break;
            case "monthly":
                start = LocalDate.now().minus(1, ChronoUnit.MONTHS).atStartOfDay();
                message = "월간 통계가 완료되었습니다.";
                break;
            default:
                throw new IllegalArgumentException("잘못된 기간 입니다.");
        }

        List<VideoStatDTO> stats = videoStatService.getTop5ByTotalPlayTime(start, end);
        return new VideoStatResponseDTO(stats, message);
    }


    @PostMapping
    public String generateAllVideoStats() {
        videoStatService.createStatisticsForAllVideos();
        return "모든 비디오에 대한 통계 데이터가 생성되었습니다.";
    }
}
