package org.sparta.jenview.ad.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdDTO {

    private Long Id;
    private Long videoId;
    private String title;
    private LocalDate createdAt;
    private int adCount;
    private boolean settledToday;

}

