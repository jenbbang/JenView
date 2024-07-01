package org.sparta.jenview.mapper;

import org.sparta.jenview.dto.VideoPlayDTO;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.entity.VideoEntity;
import org.sparta.jenview.entity.VideoPlayEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoPlayMapper implements MapperInterface<VideoPlayDTO, VideoPlayEntity> {

    // to DTOVideoPlayDTO
    public VideoPlayDTO toDTO(VideoPlayEntity videoPlayEntity) {
        VideoPlayDTO videoPlayDTO = new VideoPlayDTO();
//        videoPlayDTO.setId(videoPlayEntity.getId());
        videoPlayDTO.setUserId(videoPlayEntity.getUserEntity().getId());
        videoPlayDTO.setVideoId(videoPlayEntity.getVideoEntity().getId());
//        videoPlayDTO.setLastPlayedAt(videoPlayEntity.getLastPlayedAt());
        videoPlayDTO.setLastPlayedTime(videoPlayEntity.getLastPlayedTime());
        return videoPlayDTO;
    }

    @Override
    public VideoPlayEntity toEntity(VideoPlayDTO videoPlayDTO) {
        throw new UnsupportedOperationException("이 메서드는 지원되지 않습니다. 대신 toEntity(VideoPlayDTO, UserEntity, VideoEntity)를 사용하세요.");
    }


    // to Entity
    public VideoPlayEntity toEntity(VideoPlayDTO videoPlayDTO, UserEntity userEntity, VideoEntity videoEntity) {
        VideoPlayEntity videoPlayEntity = new VideoPlayEntity();
        videoPlayEntity.setUserEntity(userEntity);
        videoPlayEntity.setVideoEntity(videoEntity);
//        videoPlayEntity.setLastPlayedAt(videoPlayDTO.getLastPlayedAt());
        videoPlayEntity.setLastPlayedTime(videoPlayDTO.getLastPlayedTime());
        return videoPlayEntity;
    }
}
