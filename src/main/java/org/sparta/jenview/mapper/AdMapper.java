package org.sparta.jenview.mapper;

import org.sparta.jenview.dto.AdDTO;
import org.sparta.jenview.dto.AdResponseDTO;
import org.sparta.jenview.entity.AdEntity;
import org.sparta.jenview.entity.VideoEntity;
import org.springframework.stereotype.Component;

@Component
public class AdMapper {

    public AdDTO toDTO(AdEntity adEntity) {
        AdDTO adDTO = new AdDTO();
        adDTO.setId(adEntity.getId());
        adDTO.setTitle(adEntity.getTitle());
        adDTO.setDuration(adEntity.getDuration());
        adDTO.setAdCount(adEntity.getAdCount());
        adDTO.setPlayTime(adEntity.getPlayTime());
        adDTO.setStartTime(adEntity.getStartTime());
        adDTO.setCreatedAt(adEntity.getCreatedAt());
        adDTO.setUpdatedAt(adEntity.getUpdatedAt());
        adDTO.setVideoId(adEntity.getVideo().getId());
        return adDTO;
    }

    public AdEntity toEntity(AdDTO adDTO, VideoEntity videoEntity) {
        AdEntity adEntity = new AdEntity();
        adEntity.setTitle(adDTO.getTitle());
        adEntity.setDuration(adDTO.getDuration());
        adEntity.setAdCount(adDTO.getAdCount());
        adEntity.setPlayTime(adDTO.getPlayTime());
        adEntity.setStartTime(adDTO.getStartTime());
        adEntity.setVideo(videoEntity);
        return adEntity;
    }

    public AdResponseDTO toResponseDTO(AdEntity adEntity) {
        return new AdResponseDTO(adEntity);
    }
}
