package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VideoResponseDTO {
    private Long videoId;
    private Long userId;
    private String title;
    private String description;
    private Integer duration;
    private Integer viewCount;
    private Long playTime;

    // Constructor
    public VideoResponseDTO(Long videoId, Long userId, String title, String description, Integer duration, Integer viewCount, Long playTime) {
        this.videoId = videoId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.viewCount = viewCount;
        this.playTime = playTime;
    }

    // Getters and setters (or use Lombok @Data annotation)
    // ... (getters and setters omitted for brevity)
}
