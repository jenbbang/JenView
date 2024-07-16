package org.sparta.jenview.Settlement.service;

import jakarta.transaction.Transactional;
import org.sparta.jenview.Settlement.dto.SettlementResponseDTO;
import org.sparta.jenview.Settlement.dto.VideoCalcDTO;
import org.sparta.jenview.Settlement.entity.VideoCalcEntity;
import org.sparta.jenview.Settlement.mapper.VideoCalcMapper;
import org.sparta.jenview.Settlement.repository.VideoCalcRepository;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class VideoCalcService {
    private final VideoCalcRepository videoCalcRepository;
    private final VideoStatRepository videoStatRepository;
    private final AdStatRepository adStatRepository;
    private final VideoCalcMapper videoCalcMapper;

    @Autowired
    public VideoCalcService(VideoCalcRepository videoCalcRepository, VideoStatRepository videoStatRepository, AdStatRepository adStatRepository, VideoCalcMapper videoCalcMapper) {
        this.videoCalcRepository = videoCalcRepository;
        this.videoStatRepository = videoStatRepository;
        this.adStatRepository = adStatRepository;
        this.videoCalcMapper = videoCalcMapper;
    }

    @Transactional
    public void calculateDailySettlement() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfDay, endOfDay);
        for (VideoStatEntity stat : stats) {
            long totalViews = stat.getViewCount();
            double settlement = calculateSettlement(totalViews);
            long adCount = getAdCountFromAdStats(stat.getVideoId().getId(), startOfDay, endOfDay);
            double adRevenue = getAdRevenueFromAdStats(stat.getVideoId().getId(), startOfDay, endOfDay);

            VideoCalcEntity videoCalcEntity = new VideoCalcEntity();
            videoCalcEntity.setVideoId(stat.getVideoId());
            videoCalcEntity.setVideoSettlement(settlement + adRevenue);
            videoCalcEntity.setCreatedAt(LocalDateTime.now());

            videoCalcRepository.save(videoCalcEntity);
        }
    }

    public double calculateSettlement(long views) {
        double settlement = 0;

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

        return Math.floor(settlement); // 1원 단위 이하 절사
    }

    public long getAdCountFromAdStats(Long videoId, LocalDateTime start, LocalDateTime end) {
        List<AdStatEntity> adStats = adStatRepository.findByVideoIdIdAndCreatedAtBetween(videoId, start, end);
        return adStats.stream().mapToLong(AdStatEntity::getViewCount).sum();
    }

    public double getAdRevenueFromAdStats(Long videoId, LocalDateTime start, LocalDateTime end) {
        List<AdStatEntity> adStats = adStatRepository.findByVideoIdIdAndCreatedAtBetween(videoId, start, end);
        return adStats.stream().mapToDouble(adStat -> {
            long adCount = adStat.getViewCount();
            double adRevenue = 0;

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
        }).sum();
    }

    public SettlementResponseDTO getDailySettlement() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        AtomicReference<Double> totalSettlement = new AtomicReference<>(0.0);
        List<VideoCalcDTO> videoCalcDTOs = stats.stream()
                .map(stat -> {
                    long totalViews = stat.getViewCount();
                    double settlement = calculateSettlement(totalViews);
                    long adCount = getAdCountFromAdStats(stat.getVideoId().getId(), startOfDay, endOfDay);
                    double adRevenue = getAdRevenueFromAdStats(stat.getVideoId().getId(), startOfDay, endOfDay);
                    double total = settlement + adRevenue;
                    totalSettlement.updateAndGet(v -> v + total);
                    return videoCalcMapper.toDTO(stat, settlement, adRevenue, total, adCount);  // 광고 횟수 추가
                })
                .collect(Collectors.toList());

        return new SettlementResponseDTO(1L, totalSettlement.get(), startOfDay.toString(), endOfDay.toString(), videoCalcDTOs);
    }

    public SettlementResponseDTO getWeeklySettlement() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(java.time.DayOfWeek.SUNDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfWeekDateTime = endOfWeek.atTime(LocalTime.MAX);
        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfWeekDateTime, endOfWeekDateTime);

        AtomicReference<Double> totalSettlement = new AtomicReference<>(0.0);
        List<VideoCalcDTO> videoCalcDTOs = stats.stream()
                .map(stat -> {
                    long totalViews = stat.getViewCount();
                    double settlement = calculateSettlement(totalViews);
                    long adCount = getAdCountFromAdStats(stat.getVideoId().getId(), startOfWeekDateTime, endOfWeekDateTime);
                    double adRevenue = getAdRevenueFromAdStats(stat.getVideoId().getId(), startOfWeekDateTime, endOfWeekDateTime);
                    double total = settlement + adRevenue;
                    totalSettlement.updateAndGet(v -> v + total);
                    return videoCalcMapper.toDTO(stat, settlement, adRevenue, total, adCount);  // 광고 횟수 추가
                })
                .collect(Collectors.toList());

        return new SettlementResponseDTO(1L, totalSettlement.get(), startOfWeekDateTime.toString(), endOfWeekDateTime.toString(), videoCalcDTOs);
    }

    public SettlementResponseDTO getMonthlySettlement() {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);
        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfMonth, endOfMonth);

        AtomicReference<Double> totalSettlement = new AtomicReference<>(0.0);
        List<VideoCalcDTO> videoCalcDTOs = stats.stream()
                .map(stat -> {
                    long totalViews = stat.getViewCount();
                    double settlement = calculateSettlement(totalViews);
                    long adCount = getAdCountFromAdStats(stat.getVideoId().getId(), startOfMonth, endOfMonth);
                    double adRevenue = getAdRevenueFromAdStats(stat.getVideoId().getId(), startOfMonth, endOfMonth);
                    double total = settlement + adRevenue;
                    totalSettlement.updateAndGet(v -> v + total);
                    return videoCalcMapper.toDTO(stat, settlement, adRevenue, total, adCount);  // 광고 횟수 추가
                })
                .collect(Collectors.toList());

        return new SettlementResponseDTO(1L, totalSettlement.get(), startOfMonth.toString(), endOfMonth.toString(), videoCalcDTOs);
    }

    public SettlementResponseDTO getAllSettlement() {
        SettlementResponseDTO dailySettlement = getDailySettlement();
        SettlementResponseDTO weeklySettlement = getWeeklySettlement();
        SettlementResponseDTO monthlySettlement = getMonthlySettlement();

        double totalSettlement = dailySettlement.getTotalAmount() + weeklySettlement.getTotalAmount() + monthlySettlement.getTotalAmount();
        List<VideoCalcDTO> allVideoCalcs = dailySettlement.getVideoCalculations();
        allVideoCalcs.addAll(weeklySettlement.getVideoCalculations());
        allVideoCalcs.addAll(monthlySettlement.getVideoCalculations());

        return new SettlementResponseDTO(1L, totalSettlement, LocalDate.now().atStartOfDay().toString(), LocalDate.now().atTime(LocalTime.MAX).toString(), allVideoCalcs);
    }
}
