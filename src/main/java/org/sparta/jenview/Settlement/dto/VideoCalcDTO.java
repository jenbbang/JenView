package org.sparta.jenview.Settlement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoCalcDTO {

    private LocalDateTime createdAt;
    private Long videoId;
    private long viewCount;
    private long adCount;  // 광고 횟수 추가
    private double videoAmount;
    private double adAmount;
    private double totalAmount;

    public VideoCalcDTO(Long videoId, long viewCount, LocalDateTime createdAt, double videoAmount, double adAmount, double totalAmount, long adCount) {
        this.videoId = videoId;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.videoAmount = videoAmount;
        this.adAmount = adAmount;
        this.totalAmount = totalAmount;
        this.adCount = adCount;  // 광고 횟수 추가
    }

    public VideoCalcDTO() {
    }

}
