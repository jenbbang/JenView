package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VideoPlayDTO {

    private Long userId;
    private Long videoId;
    private int lastPlayedTime;
    private Integer stopTime; // 추가: 정지 시점 (초 단위)

}
