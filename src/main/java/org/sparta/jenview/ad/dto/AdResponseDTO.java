package org.sparta.jenview.ad.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.jenview.ad.entity.AdEntity;

@Getter
@Setter
@NoArgsConstructor
public class AdResponseDTO {
    private Long id;
    private Long videoId;
    private String title;
    private int adCount;
    private boolean settledToday;

    public AdResponseDTO(AdEntity adEntity) {
        this.id = adEntity.getId();
        this.videoId = adEntity.getVideo().getId();
        this.title = adEntity.getTitle();
        this.adCount = adEntity.getAdCount();
        this.settledToday = adEntity.isSettledToday();
    }

    public void setSettledToday(boolean settledToday) {
    }
}
