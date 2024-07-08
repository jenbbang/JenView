package org.sparta.jenview.ad.service;

import jakarta.transaction.Transactional;
import org.sparta.jenview.ad.dto.AdDTO;
import org.sparta.jenview.ad.dto.AdResponseDTO;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.ad.entity.AdsId;
import org.sparta.jenview.ad.mapper.AdMapper;
import org.sparta.jenview.ad.repository.AdRepository;
import org.sparta.jenview.plays.dto.AdPlayResponseDTO;
import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.plays.repository.AdPlayRepository;
import org.sparta.jenview.users.entity.UserEntity;
import org.sparta.jenview.users.repository.UserRepository;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final VideoRepository videoRepository;
    private final AdMapper adMapper;
    private final UserRepository userRepository;
    private final AdPlayRepository adPlayRepository;

    @Autowired
    public AdService(AdRepository adRepository, VideoRepository videoRepository, AdMapper adMapper, UserRepository userRepository, AdPlayRepository adPlayRepository) {
        this.adRepository = adRepository;
        this.videoRepository = videoRepository;
        this.adMapper = adMapper;
        this.userRepository = userRepository;
        this.adPlayRepository = adPlayRepository;
    }

    public List<AdResponseDTO> createAdsForVideo(AdDTO adDTO) {
        VideoEntity videoEntity = videoRepository.findById(adDTO.getVideoId())
                .orElseThrow(() -> new RuntimeException("Video not found with id " + adDTO.getVideoId()));

        int videoDuration = videoEntity.getDuration();
        int adInterval = 300; // 5분(300초)마다 광고를 배치

        List<AdResponseDTO> adResponseList = new ArrayList<>();
        for (int startTime = adInterval; startTime <= videoDuration; startTime += adInterval) {
            adDTO.setStartTime(startTime);
            AdEntity adEntity = adMapper.toEntity(adDTO);
            AdEntity savedAdEntity = adRepository.save(adEntity);
            adResponseList.add(adMapper.toResponseDTO(savedAdEntity));
        }

        return adResponseList;
    }

//    public AdResponseDTO incrementAdViewCount(Long adId) {
//        AdEntity adEntity = adRepository.findById(adId)
//                .orElseThrow(() -> new RuntimeException("Ad not found with id " + adId));
//        adEntity.incrementViewCount();
//        adRepository.save(adEntity);
//        return adMapper.toResponseDTO(adEntity);
//    }

    public List<AdResponseDTO> incrementAdViewCountsForVideo(Long videoId, int watchedDuration) {
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + videoId));

        List<AdEntity> adEntities = adRepository.findByVideoId(videoId);
        List<AdResponseDTO> adResponseList = new ArrayList<>();

//        for (AdEntity adEntity : adEntities) {
//            if (adEntity.getStartTime() <= watchedDuration) {
//                adEntity.incrementViewCount();
//
//                // 광고의 시청 시간이 광고의 길이를 초과하지 않도록 설정
//                int newPlayTime = (int) (adEntity.getPlayTime() + adEntity.getDuration());
//                if (newPlayTime > adEntity.getDuration()) {
//                    newPlayTime = adEntity.getDuration();
//                }
//                adEntity.setPlayTime(newPlayTime);
//
//                adRepository.save(adEntity);
//                adResponseList.add(adMapper.toResponseDTO(adEntity));
//            }
//        }

        return adResponseList;
    }

    @Transactional
    public void deleteAdsByVideoId(Long videoId) {
        List<AdEntity> ads = adRepository.findByVideoId(videoId);
        adRepository.deleteAll(ads);
    }

    @Transactional
    public AdPlayResponseDTO incrementView(Long adId, Long videoId, int playTime) {
        // 복합키 생성 및 값 설정
        AdsId adsId = new AdsId();
        adsId.setId(adId);
        adsId.setVideoId(videoId);

        AdEntity adEntity = adRepository.findById(adsId)
                .orElseThrow(() -> new RuntimeException("Ad not found with id " + adId + " and videoId " + videoId));
//        UserEntity userEntity = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        // 광고 시청 기록 추가
        AdPlayEntity adPlayEntity = new AdPlayEntity();
        adPlayEntity.setAdId(adId);
        adPlayEntity.setVideoId(videoId);
        adPlayEntity.setPlayTime(playTime);
        adPlayEntity.setCreatedAt(LocalDate.now());

        adPlayRepository.save(adPlayEntity);

        // 광고 조회수 및 재생 시간 업데이트
        adEntity.setAdCount(adEntity.getAdCount() + 1);
        adEntity.setPlayTime(adEntity.getPlayTime() + playTime);
        adRepository.save(adEntity);

        return adMapper.toPlayResponseDTO(adEntity);
    }
}