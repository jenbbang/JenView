package org.sparta.jenview.plays.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class VideoPlayDTO {

    private Long id;
    private Long userId;
    private Long videoId;
    private int lastPlayedTime;
    private Long stopTime = 0L; // 시청 중지 시간을 기본값 0으로 설정
    private LocalDateTime createdAt;


    public VideoPlayDTO() {

    }

}
