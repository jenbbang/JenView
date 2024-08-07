package org.sparta.jenview.videos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDTO {
    private Long id;
    private Long userId; // userId 추가
    private String title;
    private String description;
    private int viewCount;
    private int playTime;
    private int stopTime;


}