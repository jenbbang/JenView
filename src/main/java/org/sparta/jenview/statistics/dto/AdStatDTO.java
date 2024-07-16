package org.sparta.jenview.statistics.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.videos.entity.VideoEntity;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdStatDTO {
    private AdEntity adId;
    private VideoEntity videoId;
    private int viewCount;
    private LocalDateTime createdAt;
    private String rank;

    public AdStatDTO(AdEntity adId, VideoEntity videoId, int viewCount, LocalDateTime createdAt, String rank) {
        this.adId = adId;
        this.videoId = videoId;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.rank = rank;
    }

}
