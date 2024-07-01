package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.jenview.entity.AdEntity;

@Getter
@Setter
public class AdResponseDTO {
    private Long id;
    private Long videoId;
    private String title;
    private int duration;
    private int adCount;
    private long playTime;

    public AdResponseDTO(AdEntity adEntity) {
        this.id = adEntity.getId();
        this.videoId = adEntity.getVideo().getId();
        this.title = adEntity.getTitle();
        this.duration = adEntity.getDuration();
        this.adCount = adEntity.getAdCount();
        this.playTime = adEntity.getPlayTime();
    }

    // Getters and Setters
}
