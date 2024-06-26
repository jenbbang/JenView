package org.sparta.jenview.mapper;

import org.sparta.jenview.dto.VideoPlayDTO;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.entity.VideoEntity;
import org.sparta.jenview.entity.VideoPlayEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoPlayMapper {

    public VideoPlayDTO toDTO(VideoPlayEntity videoPlayEntity) {
        VideoPlayDTO videoPlayDTO = new VideoPlayDTO();
        videoPlayDTO.setId(videoPlayEntity.getId());
        videoPlayDTO.setVideoId(videoPlayEntity.getVideoEntity().getId());
        videoPlayDTO.setUserId(videoPlayEntity.getUserEntity().getId());
        videoPlayDTO.setStopTime(videoPlayEntity.getStopTime());
        return videoPlayDTO;
    }

    public VideoPlayEntity toEntity(VideoPlayDTO videoPlayDTO, UserEntity userEntity, VideoEntity videoEntity) {
        VideoPlayEntity videoPlayEntity = new VideoPlayEntity();
        videoPlayEntity.setId(videoPlayDTO.getId());
        videoPlayEntity.setVideoEntity(videoEntity);
        videoPlayEntity.setUserEntity(userEntity);
        videoPlayEntity.setStopTime(videoPlayDTO.getStopTime());
        return videoPlayEntity;
    }

}
