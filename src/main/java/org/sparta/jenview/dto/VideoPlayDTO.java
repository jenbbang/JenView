package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoPlayDTO {

    private Long userId;
    private Long videoId;
    private Long last_played_time;


}
