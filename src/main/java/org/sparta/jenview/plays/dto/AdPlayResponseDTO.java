package org.sparta.jenview.plays.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.jenview.ad.entity.AdEntity;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdPlayResponseDTO {
    private Long id;
    private Long adId;
    private Integer adCount;
    private LocalDateTime createdAt;
    private Integer adViewCount; // 광고 조회수
    // Constructor
    public AdPlayResponseDTO(Long id, Long userId, Long adId, Integer adCount, LocalDateTime createdAt, Integer adViewCount) {
        this.id = id;
        this.adId = adId;
        this.adCount = adCount;
        this.createdAt = createdAt;
        this.adViewCount = adViewCount;
    }

    public AdPlayResponseDTO(AdEntity adEntity) {
        this.id = adEntity.getId();
        this.adId = adEntity.getId();
        this.adCount = adEntity.getAdCount();
        this.createdAt = adEntity.getCreatedAt().atStartOfDay();

    }
}
