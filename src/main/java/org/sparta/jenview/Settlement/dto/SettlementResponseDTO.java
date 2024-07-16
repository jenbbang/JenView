package org.sparta.jenview.Settlement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettlementResponseDTO {
    private Long userId;
    private double totalAmount;
    private String start;
    private String end;
    private List<VideoCalcDTO> videoCalculations;

    public SettlementResponseDTO(Long userId, double totalAmount, String start, String end, List<VideoCalcDTO> videoCalculations) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.start = start;
        this.end = end;
        this.videoCalculations = videoCalculations;
    }

    public SettlementResponseDTO() {
    }
}
