package org.sparta.jenview.ad.service;

import jakarta.transaction.Transactional;
import org.sparta.jenview.ad.dto.AdResponseDTO;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.ad.mapper.AdMapper;
import org.sparta.jenview.ad.repository.AdRepository;
import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.plays.repository.AdPlayRepository;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final VideoRepository videoRepository;
    private final AdMapper adMapper;
    private final Random random = new Random();
    private final AdPlayRepository adPlayRepository;

    @Autowired
    public AdService(AdRepository adRepository, VideoRepository videoRepository, AdMapper adMapper, AdPlayRepository adPlayRepository) {
        this.adRepository = adRepository;
        this.videoRepository = videoRepository;
        this.adMapper = adMapper;
        this.adPlayRepository = adPlayRepository;
    }

    public class RandomAdGenerator {
        private static final String[] TITLES = {
                "Amazing Deal", "Limited Time Offer", "Special Discount", "Flash Sale", "Mega Savings"
        };

        private static final Random RANDOM = new Random();

        public static AdEntity generateAd() {
            AdEntity adEntity = new AdEntity();
            String randomTitle = TITLES[RANDOM.nextInt(TITLES.length)];
            adEntity.setTitle(randomTitle);
            adEntity.setAdCount(0);
            adEntity.setSettledToday(false);
            return adEntity;
        }
    }

    @Transactional
    public List<AdResponseDTO> createRandomAds(Integer numberOfAds) {
        if (numberOfAds == null || numberOfAds <= 0) {
            numberOfAds = 30; // 기본값 설정
        }

        // 모든 비디오 ID를 가져옴
        List<Long> videoIds = videoRepository.findAllVideoIds();
        if (videoIds.isEmpty()) {
            throw new RuntimeException("No videos available");
        }

        List<AdResponseDTO> adResponseDTOList = new ArrayList<>();

        for (int i = 0; i < numberOfAds; i++) {
            AdEntity randomAd = RandomAdGenerator.generateAd();

            Long randomVideoId = videoIds.get(random.nextInt(videoIds.size()));
            VideoEntity videoEntity = videoRepository.findById(randomVideoId)
                    .orElseThrow(() -> new RuntimeException("Video not found with id " + randomVideoId));

            int videoPlayTime = (int) videoEntity.getPlayTime();
            int adInterval = 300; // 5분(300초)마다 광고를 배치
            int startTime = random.nextInt((videoPlayTime / adInterval) + 1) * adInterval;

            AdEntity adEntity = new AdEntity();
            adEntity.setTitle(randomAd.getTitle());
            adEntity.setAdCount(randomAd.getAdCount());
            adEntity.setSettledToday(randomAd.isSettledToday());
            adEntity.setVideo(videoEntity);
            adEntity.setStartTime(startTime);

            AdEntity savedAdEntity = adRepository.save(adEntity);
            adResponseDTOList.add(adMapper.toResponseDTO(savedAdEntity));
        }

        return adResponseDTOList;
    }

    @Transactional
    public void recordAdPlay(Long videoId, Long stopTime, Long alreadyPlayedAds) {
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + videoId));

        // 비디오의 길이에 따라 300초마다 광고를 배치
        int adInterval = 300;
        int maxAds = (int) (videoEntity.getPlayTime() / adInterval);

        List<AdEntity> adEntities = adRepository.findByVideoId(videoId);
        int adsPlayed = alreadyPlayedAds.intValue();

        System.out.println("Total ads to be played: " + adEntities.size());
        System.out.println("Maximum ads allowed: " + maxAds);
        System.out.println("Ads already played: " + adsPlayed);

        for (AdEntity adEntity : adEntities) {
            if (adsPlayed >= maxAds) break; // 최대 광고 재생 개수에 도달하면 종료

            // 광고가 재생될 시점을 300초마다 설정
            int adStartTime = adInterval * (adsPlayed + 1);

            if (adStartTime <= stopTime) {
                adEntity.setAdCount(adEntity.getAdCount() + 1);
                adRepository.save(adEntity);

                // 최근 광고 기록 확인
                Optional<AdPlayEntity> lastAdPlayEntityOpt = adPlayRepository.findTopByVideoId_IdOrderByPlayTimeDesc(videoId);
                if (lastAdPlayEntityOpt.isPresent()) {
                    AdPlayEntity lastAdPlayEntity = lastAdPlayEntityOpt.get();
                    if (lastAdPlayEntity.getAdId().getId().equals(adEntity.getId()) && lastAdPlayEntity.getPlayTime() == adStartTime) {
                        System.out.println("Skipping ad with ID " + adEntity.getId() + " as it's the same as the last one.");
                        continue; // 직전 광고와 동일하면 넘어감
                    }
                }

                AdPlayEntity adPlayEntity = new AdPlayEntity();
                adPlayEntity.setAdId(adEntity);
                adPlayEntity.setVideoId(videoEntity);
                adPlayEntity.setPlayTime(adStartTime);

                adPlayRepository.save(adPlayEntity);
                System.out.println("Saved ad play for ad ID " + adEntity.getId() + " at play time " + adStartTime);
                adsPlayed++;
            } else {
                System.out.println("Ad start time " + adStartTime + " is greater than stop time " + stopTime);
            }
        }
    }
}