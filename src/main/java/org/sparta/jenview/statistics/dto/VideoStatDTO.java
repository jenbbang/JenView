package org.sparta.jenview.statistics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoStatDTO {
    private Long videoId;
    private String videoTitle;
    private int viewCount;
    private long totalPlayTime;
}
