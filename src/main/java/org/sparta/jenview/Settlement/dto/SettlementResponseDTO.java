package org.sparta.jenview.Settlement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettlementResponseDTO {
    private Long userId;
    private long totalAmount;
    private String start;
    private String end;
    private List<CalcDTO> videoCalculations;

    public SettlementResponseDTO(Long userId, long totalAmount, String start, String end, List<CalcDTO> videoCalculations) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.start = start;
        this.end = end;
        this.videoCalculations = videoCalculations;
    }

    public SettlementResponseDTO() {
    }
}
