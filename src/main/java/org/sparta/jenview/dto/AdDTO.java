package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdDTO {

    private Long id;
    private String title;
    private int duration;
    private int adCount;
    private long playTime;
    private Long videoId;
    private int startTime; // 광고가 시작되는 시간(초 단위)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

