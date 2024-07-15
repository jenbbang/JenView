package org.sparta.jenview.statistics.service;

import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.ad.repository.AdRepository;
import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.plays.repository.AdPlayRepository;
import org.sparta.jenview.plays.repository.VideoPlayRepository;
import org.sparta.jenview.statistics.dto.AdStatDTO;
import org.sparta.jenview.statistics.entity.AdStatEntity;
import org.sparta.jenview.statistics.mapper.AdStatMapper;
import org.sparta.jenview.statistics.repository.AdStatRepository;
import org.sparta.jenview.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AdStatService {

    private final AdStatRepository adStatRepository;
    private final VideoRepository videoRepository;
    private final VideoPlayRepository videoPlayRepository;
    private final AdStatMapper adStatMapper;
    private final AdRepository adRepository;
    private final AdPlayRepository adPlayRepository;

    @Autowired
    public AdStatService(AdPlayRepository adPlayRepository, AdStatMapper adStatMapper, AdRepository adRepository, VideoPlayRepository videoPlayRepository, AdStatRepository adStatRepository, VideoRepository videoRepository) {
        this.adStatRepository = adStatRepository;
        this.videoRepository = videoRepository;
        this.videoPlayRepository = videoPlayRepository;
        this.adStatMapper = adStatMapper;
        this.adRepository = adRepository;
        this.adPlayRepository = adPlayRepository;
    }

    public List<AdStatDTO> getTop5ByViewCount(LocalDateTime start, LocalDateTime end) {
        Pageable pageable = PageRequest.of(0, 5);
        List<AdStatEntity> entities = adStatRepository.findTop5ByViewCount(start, end, pageable);

        return IntStream.range(0, entities.size())
                .mapToObj(i -> adStatMapper.toDTO(entities.get(i), "TOP " + (i + 1)))
                .collect(Collectors.toList());
    }

    public void createAdStatistics(Long adId) {
        AdEntity adEntity = adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found with id " + adId));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<AdPlayEntity> adPlayEntities = adPlayRepository.findByAdId_IdAndCreatedAtBetween(adId, startOfDay, endOfDay);

        int totalViewCount = adPlayEntities.size();

        // adPlayEntities에서 비디오 ID를 추출하여 각 비디오에 대한 통계 생성
        adPlayEntities.stream()
                .collect(Collectors.groupingBy(adPlay -> adPlay.getVideoId().getId()))
                .forEach((videoId, adPlays) -> {
                    AdStatEntity adStatEntity = new AdStatEntity();
                    adStatEntity.setAdId(adId);
                    adStatEntity.setVideoId(videoId);
                    adStatEntity.setViewCount(adPlays.size());
                    adStatEntity.setCreatedAt(LocalDateTime.now());

                    adStatRepository.save(adStatEntity);
                });
    }

    @Transactional
    public void createStatisticsForAllAds() {
        List<AdEntity> allAds = adRepository.findAll();
        for (AdEntity ad : allAds) {
            createAdStatistics(ad.getId());
        }
    }
}