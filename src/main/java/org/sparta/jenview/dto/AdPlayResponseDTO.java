package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.jenview.entity.AdEntity;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdPlayResponseDTO {
    private Long id;
    private Long userId;
    private Long adId;
    private Integer adCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer adViewCount; // 광고 조회수
    private Integer adTotalPlayTime; // 광고 총 재생시간

    // Constructor
    public AdPlayResponseDTO(Long id, Long userId, Long adId, Integer adCount, LocalDateTime createdAt, LocalDateTime updatedAt, Integer adViewCount, Integer adTotalPlayTime) {
        this.id = id;
        this.userId = userId;
        this.adId = adId;
        this.adCount = adCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.adViewCount = adViewCount;
        this.adTotalPlayTime = adTotalPlayTime;
    }

    public AdPlayResponseDTO(AdEntity adEntity) {
    }
}
