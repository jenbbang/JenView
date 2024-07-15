package org.sparta.jenview.statistics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoStatResponseDTO {
    private List<VideoStatDTO> stats;
    private String message;

    public VideoStatResponseDTO(List<VideoStatDTO> stats, String message) {
        this.stats = stats;
        this.message = message;
    }
}
