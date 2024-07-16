package org.sparta.jenview.Settlement.controller;

import org.sparta.jenview.Settlement.dto.SettlementResponseDTO;
import org.sparta.jenview.Settlement.service.VideoCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calc")
public class VideoCalcController {

    private final VideoCalcService videoCalcService;

    @Autowired
    public VideoCalcController(VideoCalcService videoCalcService) {
        this.videoCalcService = videoCalcService;
    }

    // 수동으로 정산을 트리거하는 엔드포인트
    @PostMapping
    public String calculateDailySettlement() {
        videoCalcService.calculateDailySettlement();
        return "Daily settlement calculation triggered.";
    }

    // 현재 날짜의 일일 정산 데이터를 가져오는 엔드포인트
    @GetMapping("/daily")
    public SettlementResponseDTO getDailySettlement() {
        return videoCalcService.getDailySettlement();
    }

    // 현재 날짜를 기준으로 한 주간 정산 데이터를 가져오는 엔드포인트
    @GetMapping("/weekly")
    public SettlementResponseDTO getWeeklySettlement() {
        return videoCalcService.getWeeklySettlement();
    }

    // 현재 날짜를 기준으로 한 월간 정산 데이터를 가져오는 엔드포인트
    @GetMapping("/monthly")
    public SettlementResponseDTO getMonthlySettlement() {
        return videoCalcService.getMonthlySettlement();
    }

    // 현재 날짜를 기준으로 일, 주, 월 단위의 모든 정산 데이터를 가져오는 엔드포인트
    @GetMapping("/all")
    public SettlementResponseDTO getAllSettlement() {
        return videoCalcService.getAllSettlement();
    }
}
