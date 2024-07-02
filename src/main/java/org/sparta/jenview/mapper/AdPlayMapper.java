package org.sparta.jenview.mapper;

import org.sparta.jenview.dto.AdPlayDTO;
import org.sparta.jenview.entity.AdEntity;
import org.sparta.jenview.entity.AdPlayEntity;
import org.sparta.jenview.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AdPlayMapper {

    public AdPlayDTO toDTO(AdPlayEntity adPlayEntity) {
        AdPlayDTO adPlayDTO = new AdPlayDTO();
        adPlayDTO.setId(adPlayEntity.getId());
        adPlayDTO.setAdId(adPlayEntity.getAd().getId());
        adPlayDTO.setUserId(adPlayEntity.getUser().getId());
        adPlayDTO.setPlayTime(adPlayEntity.getPlayTime());
        return adPlayDTO;
    }

    public AdPlayEntity toEntity(AdPlayDTO adPlayDTO, AdEntity adEntity, UserEntity userEntity) {
        AdPlayEntity adPlayEntity = new AdPlayEntity();
        adPlayEntity.setAd(adEntity);
        adPlayEntity.setUser(userEntity);
        adPlayEntity.setPlayTime(adPlayDTO.getPlayTime());
        return adPlayEntity;
    }
}
