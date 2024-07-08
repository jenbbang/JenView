package org.sparta.jenview.ad.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.jenview.ad.entity.AdEntity;

@Getter
@Setter
public class AdResponseDTO {
    private Long id;
    private Long videoId;
    private String title;
    private int adCount;
    private long playTime;

    public AdResponseDTO(AdEntity adEntity) {
        this.id = adEntity.getId();
        this.videoId = adEntity.getVideoId();
        this.title = adEntity.getTitle();
        this.adCount = adEntity.getAdCount();
        this.playTime = adEntity.getPlayTime();
    }

    // Getters and Setters
}
