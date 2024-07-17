package org.sparta.jenview.Calculate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalcDTO {
    private String createdAt;
    private Long videoId;
    private long viewCount;
    private long adCount;
    private long videoSettlement;
    private long adSettlement;
    private long totalSettlement;

    public CalcDTO(Long videoId, long viewCount, String createdAt, long videoSettlement, long adSettlement, long totalSettlement, long adCount) {
        this.videoId = videoId;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.videoSettlement = videoSettlement;
        this.adSettlement = adSettlement;
        this.totalSettlement = totalSettlement;
        this.adCount = adCount;
    }

    public CalcDTO() {
    }
}
