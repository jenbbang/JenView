package org.sparta.jenview.ad.mapper;

import org.sparta.jenview.ad.dto.AdDTO;
import org.sparta.jenview.ad.dto.AdResponseDTO;
import org.sparta.jenview.ad.entity.AdEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdMapper {

    public AdDTO toDTO(AdEntity adEntity) {
        AdDTO adDTO = new AdDTO();
        adDTO.setId(adEntity.getId());
        adDTO.setVideoId(adEntity.getVideo().getId());
        adDTO.setTitle(adEntity.getTitle());
        adDTO.setAdCount(adEntity.getAdCount());
        adDTO.setCreatedAt(adEntity.getCreatedAt());
        return adDTO;
    }

    public AdEntity toEntity(AdDTO adDTO) {
        AdEntity adEntity = new AdEntity();
        adEntity.setId(adDTO.getId());
        adEntity.setTitle(adDTO.getTitle());
        adEntity.setAdCount(adDTO.getAdCount());
        adEntity.setCreatedAt(adDTO.getCreatedAt());
        // VideoEntity는 나중에 설정 (서비스에서 설정)
        return adEntity;
    }


    public AdResponseDTO toResponseDTO(AdEntity adEntity) {
        AdResponseDTO adResponseDTO = new AdResponseDTO();
        adResponseDTO.setId(adEntity.getId());
        if (adEntity.getVideo() != null) {
            adResponseDTO.setVideoId(adEntity.getVideo().getId()); // 비디오 ID 설정
        }
        adResponseDTO.setTitle(adEntity.getTitle());
        adResponseDTO.setAdCount(adEntity.getAdCount());
        adResponseDTO.setSettledToday(adEntity.isSettledToday());
        return adResponseDTO;
    }

    public List<AdResponseDTO> toResponseDTOs(List<AdEntity> adEntities) {
        return adEntities.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
