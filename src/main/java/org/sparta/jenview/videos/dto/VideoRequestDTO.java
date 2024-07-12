package org.sparta.jenview.videos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoRequestDTO {
    private Long userId;
    private String title;
    private String description;
    private int viewCount;
    private long playTime;
    private int stopTime;
}
