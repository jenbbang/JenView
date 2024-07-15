package org.sparta.jenview.statistics.controller;

import org.sparta.jenview.statistics.dto.AdStatDTO;
import org.sparta.jenview.statistics.dto.AdStatResponseDTO;
import org.sparta.jenview.statistics.dto.VideoStatResponseDTO;
import org.sparta.jenview.statistics.service.AdStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/ad-stats")
public class AdStatController {

    private final AdStatService adStatService;

    @Autowired
    public AdStatController(AdStatService adStatService) {
        this.adStatService = adStatService;
    }

    @GetMapping("/top5/viewcount")
    public AdStatResponseDTO getTop5ByViewCount(
            @RequestParam(name = "period", defaultValue = "daily") String period) {
        LocalDateTime start, end = LocalDate.now().atTime(LocalTime.MAX);
        String message = "";

        switch (period.toLowerCase()) {
            case "daily":
                start = LocalDate.now().atStartOfDay();
                message = "일 정산이 완료되었습니다.";
                break;
            case "weekly":
                start = LocalDate.now().minus(1, ChronoUnit.WEEKS).atStartOfDay();
                message = "주간 정산이 완료되었습니다.";
                break;
            case "monthly":
                start = LocalDate.now().minus(1, ChronoUnit.MONTHS).atStartOfDay();
                message = "월간 정산이 완료되었습니다.";
                break;
            default:
                throw new IllegalArgumentException("잘못된 기간 입니다.");
        }

        List<AdStatDTO> stats = adStatService.getTop5ByViewCount(start, end);
        return new AdStatResponseDTO(stats, message);
    }

    @PostMapping
    public String generateAllAdStats() {
        adStatService.createStatisticsForAllAds();
        return "모든 광고에 대한 통계 데이터가 생성되었습니다.";
    }
}
