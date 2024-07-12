package org.sparta.jenview.videos.mapper;

import org.sparta.jenview.jwt_security.mapper.MapperInterface;
import org.sparta.jenview.plays.dto.VideoPlayDTO;
import org.sparta.jenview.users.entity.UserEntity;
import org.sparta.jenview.videos.dto.VideoDTO;
import org.sparta.jenview.videos.dto.VideoResponseDTO;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VideoMapper implements MapperInterface<VideoDTO, VideoEntity> {

    // to DTO
    public VideoDTO toDTO(VideoEntity videoEntity) {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(videoEntity.getId());
        videoDTO.setTitle(videoEntity.getTitle());
        videoDTO.setDescription(videoEntity.getDescription());
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
        videoEntity.setViewCount(videoDTO.getViewCount());
        videoEntity.setPlayTime(videoDTO.getPlayTime());
        return videoEntity;
    }

    // to VideoResponseDTO
    public VideoResponseDTO toResponseDTO(VideoEntity videoEntity, Long stopTime, Long userId) {
        VideoResponseDTO dto = new VideoResponseDTO();
        dto.setId(videoEntity.getId());
        dto.setUserId(userId);
        dto.setTitle(videoEntity.getTitle());
        dto.setDescription(videoEntity.getDescription());
        dto.setViewCount(videoEntity.getViewCount());
        dto.setStopTime(stopTime); // 시청 중지 시간 설정// 시청 중지 시간 설정
        return dto;
    }

}