package org.sparta.jenview.ad.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdDTO {

    private Long id;
    private Long videoId;
    private Long userId;
    private String title;
    private int duration;
    private int adCount;
    private long playTime;
    private int startTime; // 광고가 시작되는 시간(초 단위)
    private LocalDate createdAt;

}

