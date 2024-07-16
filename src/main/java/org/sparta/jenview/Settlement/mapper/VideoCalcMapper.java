package org.sparta.jenview.Settlement.mapper;

import org.sparta.jenview.Settlement.dto.VideoCalcDTO;
import org.sparta.jenview.Settlement.entity.VideoCalcEntity;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoCalcMapper {

    public VideoCalcDTO toDTO(VideoStatEntity videoStatEntity, double videoAmount, double adAmount, double totalAmount, long adCount) {
        return new VideoCalcDTO(
                videoStatEntity.getVideoId().getId(),
                videoStatEntity.getViewCount(),
                videoStatEntity.getCreatedAt(),
                videoAmount,
                adAmount,
                totalAmount,
                adCount  // 광고 횟수 추가
        );
    }

    public VideoCalcEntity toEntity(VideoCalcDTO videoCalcDTO) {
        VideoCalcEntity videoCalcEntity = new VideoCalcEntity();
        videoCalcEntity.setVideoId(new VideoEntity());
        videoCalcEntity.getVideoId().setId(videoCalcDTO.getVideoId());
        videoCalcEntity.setVideoSettlement(videoCalcDTO.getTotalAmount());
        videoCalcEntity.setCreatedAt(videoCalcDTO.getCreatedAt());
        return videoCalcEntity;
    }
}
