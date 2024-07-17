package org.sparta.jenview.Calculate.service;

import jakarta.transaction.Transactional;
import org.sparta.jenview.Calculate.entity.AdCalcEntity;
import org.sparta.jenview.Calculate.entity.VideoCalcEntity;
import org.sparta.jenview.Calculate.repository.AdCalcRepository;
import org.sparta.jenview.Calculate.repository.VideoCalcRepository;
import org.sparta.jenview.statistics.entity.AdStatEntity;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.sparta.jenview.statistics.repository.AdStatRepository;
import org.sparta.jenview.statistics.repository.VideoStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class CalcService {

    private final VideoCalcRepository videoCalcRepository;
    private final VideoStatRepository videoStatRepository;
    private final AdStatRepository adStatRepository;
    private final AdCalcRepository adCalcRepository;

    @Autowired
    public CalcService(AdCalcRepository adCalcRepository, VideoCalcRepository videoCalcRepository, VideoStatRepository videoStatRepository, AdStatRepository adStatRepository) {
        this.videoCalcRepository = videoCalcRepository;
        this.videoStatRepository = videoStatRepository;
        this.adStatRepository = adStatRepository;
        this.adCalcRepository = adCalcRepository;
    }

    @Transactional
    public void calculateDailyVideoSettlement() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        if (videoCalcRepository.existsByCreatedAtBetween(startOfDay, endOfDay)) {
            return;
        }

        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        for (VideoStatEntity stat : stats) {
            try {
                long totalViews = stat.getViewCount();
                long settlement = calculateSettlement(totalViews);

                VideoCalcEntity videoCalcEntity = new VideoCalcEntity();
                videoCalcEntity.setVideoId(stat.getVideoId());
                videoCalcEntity.setVideoSettlement(settlement);
                videoCalcEntity.setCreatedAt(LocalDateTime.now());
                videoCalcRepository.save(videoCalcEntity);
            } catch (Exception e) {
                // Handle exception as needed
            }
        }
    }

    @Transactional
    public void calculateDailyAdSettlement() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        if (adCalcRepository.existsByCreatedAtBetween(startOfDay, endOfDay)) {
            return;
        }

        List<AdStatEntity> stats = adStatRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        for (AdStatEntity stat : stats) {
            try {
                long adRevenue = calculateAdRevenue(stat.getViewCount());

                AdCalcEntity adCalcEntity = new AdCalcEntity();
                adCalcEntity.setAdId(stat.getAdId());
                adCalcEntity.setVideoId(stat.getVideoId());
                adCalcEntity.setAdSettlement(adRevenue);
                adCalcEntity.setCreatedAt(LocalDateTime.now());
                adCalcRepository.save(adCalcEntity);
            } catch (Exception e) {
                // Handle exception as needed
            }
        }
    }

    public long calculateSettlement(long views) {
        long settlement = 0;

        if (views > 1000000) {
            settlement += (views - 1000000) * 1.5;
            views = 1000000;
        }
        if (views > 500000) {
            settlement += (views - 500000) * 1.3;
            views = 500000;
        }
        if (views > 100000) {
            settlement += (views - 100000) * 1.1;
            views = 100000;
        }
        if (views > 0) {
            settlement += views * 1;
        }

        return settlement;
    }

    public long calculateAdRevenue(long adCount) {
        long adRevenue = 0;

        if (adCount > 1000000) {
            adRevenue += (adCount - 1000000) * 20;
            adCount = 1000000;
        }
        if (adCount > 500000) {
            adRevenue += (adCount - 500000) * 15;
            adCount = 500000;
        }
        if (adCount > 100000) {
            adRevenue += (adCount - 100000) * 12;
            adCount = 100000;
        }
        if (adCount > 0) {
            adRevenue += adCount * 10;
        }

        return adRevenue;
    }
}
