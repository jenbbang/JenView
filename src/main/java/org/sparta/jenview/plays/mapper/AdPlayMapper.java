package org.sparta.jenview.plays.mapper;

import org.sparta.jenview.plays.dto.AdPlayDTO;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.users.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AdPlayMapper {

    public AdPlayDTO toDTO(AdPlayEntity adPlayEntity) {
        AdPlayDTO adPlayDTO = new AdPlayDTO();
        adPlayDTO.setId(adPlayEntity.getId());
        adPlayDTO.setAdId(adPlayEntity.getAd().getId());
        adPlayDTO.setPlayTime(adPlayEntity.getPlayTime());
        return adPlayDTO;
    }

    public AdPlayEntity toEntity(AdPlayDTO adPlayDTO, AdEntity adEntity, UserEntity userEntity) {
        AdPlayEntity adPlayEntity = new AdPlayEntity();
        adPlayEntity.setAd(adEntity);
        adPlayEntity.setPlayTime(adPlayDTO.getPlayTime());
        return adPlayEntity;
    }
}
