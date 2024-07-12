package org.sparta.jenview.plays.mapper;

import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.plays.dto.AdPlayDTO;
import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.springframework.stereotype.Component;

@Component
public class AdPlayMapper {

    public AdPlayDTO toDTO(AdPlayEntity adPlayEntity) {
        AdPlayDTO adPlayDTO = new AdPlayDTO();
//        adPlayDTO.setId(adPlayEntity.getId());
        adPlayDTO.setAdId(adPlayEntity.getAdId().getId());
        adPlayDTO.setPlayTime(adPlayEntity.getPlayTime());
        return adPlayDTO;
    }

    public AdPlayEntity toEntity(AdPlayDTO adPlayDTO, AdEntity adEntity) {
        AdPlayEntity adPlayEntity = new AdPlayEntity();
        adPlayEntity.setAdId(adEntity);
        adPlayEntity.setPlayTime(adPlayDTO.getPlayTime());
        return adPlayEntity;
    }

    public Object toResponseDTO(AdPlayEntity adPlayEntity) {
        adPlayEntity.getId();
        adPlayEntity.getCreatedAt();
        adPlayEntity.getCreatedAt();

        return adPlayEntity;

    }
}
