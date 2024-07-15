package org.sparta.jenview.statistics.mapper;

import org.sparta.jenview.statistics.dto.AdStatDTO;
import org.sparta.jenview.statistics.entity.AdStatEntity;
import org.springframework.stereotype.Component;

@Component
public class AdStatMapper {

    public AdStatDTO toDTO(AdStatEntity adStatEntity, String rank) {
        return new AdStatDTO(
                adStatEntity.getAdId(),
                adStatEntity.getVideoId(),
                adStatEntity.getViewCount(),
                adStatEntity.getCreatedAt(),
                rank
        );
    }

    public AdStatEntity toEntity(AdStatDTO adStatDTO) {
        AdStatEntity adStatEntity = new AdStatEntity();
        adStatEntity.setAdId(adStatDTO.getAdId());
        adStatEntity.setVideoId(adStatDTO.getVideoId());
        adStatEntity.setViewCount(adStatDTO.getViewCount());
        adStatEntity.setCreatedAt(adStatDTO.getCreatedAt());
        return adStatEntity;
    }
}
