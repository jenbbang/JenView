package org.sparta.jenview.statistics.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdStatDTO {
    private Long adId;
    private Long videoId;
    private int viewCount;
    private LocalDateTime createdAt;
    private String rank;

    public AdStatDTO(Long adId, Long videoId, int viewCount, LocalDateTime createdAt, String rank) {
        this.adId = adId;
        this.videoId = videoId;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.rank = rank;
    }
}
