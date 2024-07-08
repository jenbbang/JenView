package org.sparta.jenview.videos.mapper;

import org.sparta.jenview.jwt_security.mapper.MapperInterface;
import org.sparta.jenview.videos.dto.VideoDTO;
import org.sparta.jenview.videos.dto.VideoResponseDTO;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper implements MapperInterface<VideoDTO, VideoEntity> {

    // to DTO
    public VideoDTO toDTO(VideoEntity videoEntity) {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(videoEntity.getId());
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
        videoEntity.setId(videoDTO.getId());
        videoEntity.setTitle(videoDTO.getTitle());
        videoEntity.setDescription(videoDTO.getDescription());
        videoEntity.setDuration(videoDTO.getDuration());
        videoEntity.setViewCount(videoDTO.getViewCount());
        videoEntity.setPlayTime(videoDTO.getPlayTime());
        return videoEntity;
    }
    // to VideoResponseDTO
    public VideoResponseDTO toResponseDTO(VideoEntity videoEntity, Long userId) {
        return new VideoResponseDTO(
                videoEntity.getId(),
                userId,
                videoEntity.getTitle(),
                videoEntity.getDescription(),
                videoEntity.getDuration(),
                videoEntity.getViewCount(),
                videoEntity.getPlayTime()
        );
    }
}
