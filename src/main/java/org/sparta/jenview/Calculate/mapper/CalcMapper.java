package org.sparta.jenview.Calculate.mapper;

import org.sparta.jenview.Calculate.dto.CalcDTO;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.springframework.stereotype.Component;

@Component
public class CalcMapper {

    public CalcDTO toDTO(VideoStatEntity stat, long videoSettlement, long adSettlement, long totalSettlement, long adCount) {
        return new CalcDTO(
                stat.getVideoId().getId(),
                stat.getViewCount(),
                stat.getCreatedAt().toString(),
                videoSettlement,
                adSettlement,
                totalSettlement,
                adCount
        );
    }
}