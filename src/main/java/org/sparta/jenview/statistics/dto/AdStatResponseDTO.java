package org.sparta.jenview.statistics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdStatResponseDTO {
    private List<AdStatDTO> stats;
    private String message;

    public AdStatResponseDTO(List<AdStatDTO> stats, String message) {
        this.stats = stats;
        this.message = message;
    }
}
