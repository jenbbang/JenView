package org.sparta.jenview.plays.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdPlayDTO {
    private Long id;
    private Long adId;
    private Long userId;
    private int playTime;
}
