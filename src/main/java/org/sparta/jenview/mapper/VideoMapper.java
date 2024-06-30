package org.sparta.jenview.mapper;

import org.sparta.jenview.dto.VideoDTO;
import org.sparta.jenview.entity.VideoEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper implements MapperInterface<VideoDTO, VideoEntity> {

    // to DTO
    public VideoDTO toDTO(VideoEntity videoEntity) {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle(videoEntity.getTitle());
        videoDTO.setDescription(videoEntity.getDescription());
        videoDTO.setDuration(videoEntity.getDuration());
        videoDTO.setViewCount(videoEntity.getViewCount());
        videoDTO.setPlayTime((int) videoEntity.getPlayTime());
        videoDTO.setUserId(videoEntity.getUserEntity().getId()); // userId 설정
        return videoDTO;
    }

    // to Entity
    public VideoEntity toEntity(VideoDTO videoDTO) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle(videoDTO.getTitle());
        videoEntity.setDescription(videoDTO.getDescription());
        videoEntity.setDuration(videoDTO.getDuration());
        videoEntity.setViewCount(videoDTO.getViewCount());
        videoEntity.setPlayTime(videoDTO.getPlayTime());
        return videoEntity;
    }
}
