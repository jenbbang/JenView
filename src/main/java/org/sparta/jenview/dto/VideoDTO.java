package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDTO {

    private Long userId; // userId 추가
    private String title;
    private String description;
    private int duration;
    private int viewCount;
    private int playTime;

}
