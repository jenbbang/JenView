package org.sparta.jenview.videos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VideoResponseDTO {

    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Integer viewCount;
    private Long stopTime;

    // Constructor
    public VideoResponseDTO(Long stopTime, Long id,Long userId, String title, String description, Integer viewCount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.stopTime = stopTime;
    }

    public VideoResponseDTO() {

    }
}
