package org.sparta.jenview.ad.mapper;

import org.sparta.jenview.ad.dto.AdDTO;
import org.sparta.jenview.plays.dto.AdPlayResponseDTO;
import org.sparta.jenview.ad.dto.AdResponseDTO;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.stereotype.Component;

@Component
public class AdMapper {

    public AdDTO toDTO(AdEntity adEntity) {
        AdDTO adDTO = new AdDTO();
        adDTO.setId(adEntity.getId());
        adDTO.setTitle(adEntity.getTitle());
        adDTO.setAdCount(adEntity.getAdCount());
        adDTO.setPlayTime(adEntity.getPlayTime());
        adDTO.setStartTime(adEntity.getStartTime());
        adDTO.setCreatedAt(adEntity.getCreatedAt());
        adDTO.setVideoId(adEntity.getVideoId());
        return adDTO;
    }

    public AdEntity toEntity(AdDTO adDTO) {
        AdEntity adEntity = new AdEntity();
        adEntity.setTitle(adDTO.getTitle());
        adEntity.setAdCount(adDTO.getAdCount());
        adEntity.setPlayTime(adDTO.getPlayTime());
        adEntity.setStartTime(adDTO.getStartTime());
        adEntity.setVideoId(adDTO.getVideoId());
        return adEntity;
    }

    public AdResponseDTO toResponseDTO(AdEntity adEntity) {
        return new AdResponseDTO(adEntity);
    }

    public AdPlayResponseDTO toPlayResponseDTO(AdEntity adEntity) {
        return new AdPlayResponseDTO(adEntity);
    }
}
