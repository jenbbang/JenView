package org.sparta.jenview.mapper;

import org.sparta.jenview.dto.VideoPlayDTO;
import org.sparta.jenview.entity.VideoPlayEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoPlayMapper implements MapperInterface<VideoPlayDTO, VideoPlayEntity> {

    // to DTO
    public VideoPlayDTO toDTO(VideoPlayEntity videoPlayEntity) {
        VideoPlayDTO videoPlayDTO = new VideoPlayDTO();
        videoPlayDTO.setVideoId(videoPlayEntity.getVideoEntity().getId());
        videoPlayDTO.setUserId(videoPlayEntity.getUserEntity().getId());
        videoPlayDTO.setLastPlayedTime(videoPlayEntity.getLastPlayedTime());
        return videoPlayDTO;
    }

    // to Entity
    public VideoPlayEntity toEntity(VideoPlayDTO videoPlayDTO) {
        VideoPlayEntity videoPlayEntity = new VideoPlayEntity();
        videoPlayEntity.setLastPlayedTime(videoPlayDTO.getLastPlayedTime());
        return videoPlayEntity;
    }


}
