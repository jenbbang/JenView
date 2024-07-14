package org.sparta.jenview.statistics.mapper;

import org.sparta.jenview.statistics.dto.VideoStatDTO;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoStatMapper {

    public VideoStatDTO toDTO(VideoStatEntity videoStatEntity) {
        VideoStatDTO videoStatDTO = new VideoStatDTO();
        videoStatDTO.setVideoId(videoStatEntity.getVideoId().getId());
        videoStatDTO.setVideoTitle(videoStatEntity.getVideoId().getTitle());
        videoStatDTO.setViewCount(videoStatEntity.getViewCount());
        videoStatDTO.setTotalPlayTime(videoStatEntity.getTotalPlayTime());
        return videoStatDTO;
    }

    public VideoStatEntity toEntity(VideoStatDTO videoStatDTO, VideoEntity videoEntity) {
        VideoStatEntity videoStatEntity = new VideoStatEntity();
        videoStatEntity.setVideoId(videoEntity);
        videoStatEntity.setViewCount(videoStatDTO.getViewCount());
        videoStatEntity.setTotalPlayTime(videoStatDTO.getTotalPlayTime());
        return videoStatEntity;
    }
}
