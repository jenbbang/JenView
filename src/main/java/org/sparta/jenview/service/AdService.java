package org.sparta.jenview.service;

import org.sparta.jenview.dto.AdDTO;
import org.sparta.jenview.dto.AdResponseDTO;
import org.sparta.jenview.entity.AdEntity;
import org.sparta.jenview.entity.VideoEntity;
import org.sparta.jenview.mapper.AdMapper;
import org.sparta.jenview.repository.AdRepository;
import org.sparta.jenview.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final VideoRepository videoRepository;
    private final AdMapper adMapper;

    @Autowired
    public AdService(AdRepository adRepository, VideoRepository videoRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.videoRepository = videoRepository;
        this.adMapper = adMapper;
    }
    public List<AdResponseDTO> createAdsForVideo(AdDTO adDTO) {
        VideoEntity videoEntity = videoRepository.findById(adDTO.getVideoId())
                .orElseThrow(() -> new RuntimeException("Video not found with id " + adDTO.getVideoId()));

        int videoDuration = videoEntity.getDuration();
        int adInterval = 300; // 5분(300초)마다 광고를 배치

        List<AdResponseDTO> adResponseList = new ArrayList<>();
        for (int startTime = adInterval; startTime <= videoDuration; startTime += adInterval) {
            adDTO.setStartTime(startTime);
            AdEntity adEntity = adMapper.toEntity(adDTO, videoEntity);
            AdEntity savedAdEntity = adRepository.save(adEntity);
            adResponseList.add(adMapper.toResponseDTO(savedAdEntity));
        }

        return adResponseList;
    }

    public AdResponseDTO incrementAdViewCount(Long adId) {
        AdEntity adEntity = adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found with id " + adId));
        adEntity.incrementViewCount();
        adRepository.save(adEntity);
        return adMapper.toResponseDTO(adEntity);
    }

    public List<AdResponseDTO> incrementAdViewCountsForVideo(Long videoId, int watchedDuration) {
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + videoId));

        List<AdEntity> adEntities = adRepository.findByVideoId(videoId);
        List<AdResponseDTO> adResponseList = new ArrayList<>();

        for (AdEntity adEntity : adEntities) {
            if (adEntity.getStartTime() <= watchedDuration) {
                adEntity.incrementViewCount();
                adRepository.save(adEntity);
                adResponseList.add(adMapper.toResponseDTO(adEntity));
            }
        }

        return adResponseList;
    }
}