package org.sparta.jenview.Calculate.controller;

import org.sparta.jenview.Calculate.dto.SettlementResponseDTO;
import org.sparta.jenview.Calculate.service.VideoCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calc")
public class VideoCalcController {

    private final VideoCalcService calcService;

    @Autowired
    public VideoCalcController(VideoCalcService calcService) {
        this.calcService = calcService;
    }

    // 수동으로 정산을 트리거하는 엔드포인트
    @PostMapping
    public String calculateDailySettlement() {
        calcService.calculateDailySettlement();
        return "일일 정산 계산이 완료 되었습니다.";
    }

    // 현재 날짜의 일일 정산 데이터를 가져오는 엔드포인트
    @GetMapping("/daily")
    public SettlementResponseDTO getDailySettlement() {
        return calcService.getDailySettlement();
    }

    // 현재 날짜를 기준으로 한 주간 정산 데이터를 가져오는 엔드포인트
    @GetMapping("/weekly")
    public SettlementResponseDTO getWeeklySettlement() {
        return calcService.getWeeklySettlement();
    }

    // 현재 날짜를 기준으로 한 월간 정산 데이터를 가져오는 엔드포인트
    @GetMapping("/monthly")
    public SettlementResponseDTO getMonthlySettlement() {
        return calcService.getMonthlySettlement();
    }

    // 현재 날짜를 기준으로 일, 주, 월 단위의 모든 정산 데이터를 가져오는 엔드포인트
    @GetMapping("/all")
    public SettlementResponseDTO getAllSettlement() {
        return calcService.getAllSettlement();
    }
}
