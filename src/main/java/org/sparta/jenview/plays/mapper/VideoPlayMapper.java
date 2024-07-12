package org.sparta.jenview.plays.mapper;

import org.sparta.jenview.plays.dto.VideoPlayDTO;
import org.sparta.jenview.users.entity.UserEntity;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoPlayMapper {

    public VideoPlayDTO toDTO(VideoPlayEntity videoPlayEntity) {
        VideoPlayDTO videoPlayDTO = new VideoPlayDTO();
        videoPlayDTO.setId(videoPlayEntity.getId());
        videoPlayDTO.setVideoId(videoPlayEntity.getVideoEntity().getId());
        videoPlayDTO.setUserId(videoPlayEntity.getUserEntity().getId());
        videoPlayDTO.setStopTime(Long.valueOf(videoPlayEntity.getStopTime()));
        videoPlayDTO.setCreatedAt(videoPlayEntity.getCreatedAt());
        return videoPlayDTO;
    }

    public VideoPlayEntity toEntity(VideoPlayDTO videoPlayDTO, UserEntity userEntity, VideoEntity videoEntity) {
        VideoPlayEntity videoPlayEntity = new VideoPlayEntity();
        videoPlayEntity.setId(videoPlayDTO.getId());
        videoPlayEntity.setVideoEntity(videoEntity);
        videoPlayEntity.setUserEntity(userEntity);
        videoPlayEntity.setStopTime(videoPlayDTO.getStopTime());
        videoPlayEntity.setCreatedAt(videoPlayDTO.getCreatedAt());
        return videoPlayEntity;
    }

}
