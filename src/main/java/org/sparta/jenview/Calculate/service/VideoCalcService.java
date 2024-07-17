package org.sparta.jenview.Calculate.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparta.jenview.Calculate.dto.CalcDTO;
import org.sparta.jenview.Calculate.dto.SettlementResponseDTO;
import org.sparta.jenview.Calculate.entity.AdCalcEntity;
import org.sparta.jenview.Calculate.entity.VideoCalcEntity;
import org.sparta.jenview.Calculate.mapper.CalcMapper;
import org.sparta.jenview.Calculate.repository.AdCalcRepository;
import org.sparta.jenview.Calculate.repository.VideoCalcRepository;
import org.sparta.jenview.statistics.entity.AdStatEntity;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.sparta.jenview.statistics.repository.AdStatRepository;
import org.sparta.jenview.statistics.repository.VideoStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class VideoCalcService {

    private static final Logger logger = LoggerFactory.getLogger(CalcService.class);

    private final VideoCalcRepository videoCalcRepository;
    private final VideoStatRepository videoStatRepository;
    private final AdStatRepository adStatRepository;
    private final CalcMapper calcMapper;
    private final AdCalcRepository adCalcRepository;

    @Autowired
    public VideoCalcService(AdCalcRepository adCalcRepository, VideoCalcRepository videoCalcRepository, VideoStatRepository videoStatRepository, AdStatRepository adStatRepository, CalcMapper calcMapper) {
        this.videoCalcRepository = videoCalcRepository;
        this.videoStatRepository = videoStatRepository;
        this.adStatRepository = adStatRepository;
        this.adCalcRepository = adCalcRepository;
        this.calcMapper = calcMapper;
    }

    @Transactional
    public void calculateDailySettlement() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfDay, endOfDay);
        for (VideoStatEntity stat : stats) {
            long totalViews = stat.getViewCount();
            long settlement = calculateSettlement(totalViews);
            long adCount = getAdCountFromAdStats(stat.getVideoId().getId(), startOfDay, endOfDay);
            long adRevenue = getAdRevenueFromAdStats(stat.getVideoId().getId(), startOfDay, endOfDay);

            // 두 개를 더한 후 1원 단위 이하 절사
            long totalSettlement = Math.floorDiv(settlement + adRevenue, 1);

            // 비디오 정산 저장
            VideoCalcEntity videoCalcEntity = new VideoCalcEntity();
            videoCalcEntity.setVideoId(stat.getVideoId());
            videoCalcEntity.setVideoSettlement(settlement);
            videoCalcEntity.setCreatedAt(LocalDateTime.now());
            logger.info("Saving VideoCalcEntity: {}", videoCalcEntity);
            videoCalcRepository.save(videoCalcEntity);

            // 광고 정산 저장
            List<AdStatEntity> adStats = adStatRepository.findByVideoIdIdAndCreatedAtBetween(stat.getVideoId().getId(), startOfDay, endOfDay);
            for (AdStatEntity adStat : adStats) {
                AdCalcEntity adCalcEntity = new AdCalcEntity();
                adCalcEntity.setAdId(adStat.getAdId());
                adCalcEntity.setVideoId(stat.getVideoId());
                adCalcEntity.setAdSettlement(adRevenue);
                adCalcEntity.setCreatedAt(LocalDateTime.now());
                logger.info("Saving AdCalcEntity: {}", adCalcEntity);
                adCalcRepository.save(adCalcEntity);
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

    public long getAdCountFromAdStats(Long videoId, LocalDateTime start, LocalDateTime end) {
        List<AdStatEntity> adStats = adStatRepository.findByVideoIdIdAndCreatedAtBetween(videoId, start, end);
        return adStats.stream().mapToLong(AdStatEntity::getViewCount).sum();
    }

    public long getAdRevenueFromAdStats(Long videoId, LocalDateTime start, LocalDateTime end) {
        List<AdStatEntity> adStats = adStatRepository.findByVideoIdIdAndCreatedAtBetween(videoId, start, end);
        return adStats.stream().mapToLong(adStat -> {
            long adCount = adStat.getViewCount();
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
        }).sum();
    }


    public SettlementResponseDTO getDailySettlement() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        AtomicReference<Long> totalSettlement = new AtomicReference<>(0L);
        List<CalcDTO> videoCalcDTOs = stats.stream()
                .map(stat -> {
                    long totalViews = stat.getViewCount();
                    long settlement = calculateSettlement(totalViews);
                    long adCount = getAdCountFromAdStats(stat.getVideoId().getId(), startOfDay, endOfDay);
                    long adRevenue = getAdRevenueFromAdStats(stat.getVideoId().getId(), startOfDay, endOfDay);
                    long total = Math.floorDiv(settlement + adRevenue, 1); // 두 개를 더한 후 1원 단위 이하 절사
                    totalSettlement.updateAndGet(v -> v + total);
                    return calcMapper.toDTO(stat, settlement, adRevenue, total, adCount);  // 광고 횟수 추가
                })
                .collect(Collectors.toList());

        return new SettlementResponseDTO(1L, totalSettlement.get(), startOfDay.toString(), endOfDay.toString(), videoCalcDTOs);
    }

    public SettlementResponseDTO getWeeklySettlement() {
        LocalDate now = LocalDate.now();

        // 현재 날짜가 포함된 주의 시작일(일요일)과 종료일(토요일)을 계산
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfWeekDateTime = endOfWeek.atTime(LocalTime.MAX);

        // 해당 주의 정산된 데이터가 있는지 확인
        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfWeekDateTime, endOfWeekDateTime);

        if (stats.isEmpty()) {
            return new SettlementResponseDTO(1L, 0, startOfWeekDateTime.toString(), endOfWeekDateTime.toString(), List.of());
        }

        // 실제 데이터가 있는 첫 번째 날짜 찾기
        LocalDateTime actualStartDateTime = stats.stream()
                .min(Comparator.comparing(VideoStatEntity::getCreatedAt))
                .map(VideoStatEntity::getCreatedAt)
                .orElse(startOfWeekDateTime);

        AtomicReference<Long> totalSettlement = new AtomicReference<>(0L);
        List<CalcDTO> videoCalcDTOs = stats.stream()
                .filter(stat -> !stat.getCreatedAt().isBefore(actualStartDateTime))
                .map(stat -> {
                    long totalViews = stat.getViewCount();
                    long settlement = calculateSettlement(totalViews);
                    long adCount = getAdCountFromAdStats(stat.getVideoId().getId(), actualStartDateTime, endOfWeekDateTime);
                    long adRevenue = getAdRevenueFromAdStats(stat.getVideoId().getId(), actualStartDateTime, endOfWeekDateTime);
                    long total = Math.floorDiv(settlement + adRevenue, 1); // 두 개를 더한 후 1원 단위 이하 절사
                    totalSettlement.updateAndGet(v -> v + total);
                    return calcMapper.toDTO(stat, settlement, adRevenue, total, adCount);  // 광고 횟수 추가
                })
                .collect(Collectors.toList());

        return new SettlementResponseDTO(1L, totalSettlement.get(), actualStartDateTime.toString(), endOfWeekDateTime.toString(), videoCalcDTOs);
    }

    public SettlementResponseDTO getMonthlySettlement() {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);
        List<VideoStatEntity> stats = videoStatRepository.findByCreatedAtBetween(startOfMonth, endOfMonth);

        AtomicReference<Long> totalSettlement = new AtomicReference<>(0L);
        List<CalcDTO> videoCalcDTOs = stats.stream()
                .map(stat -> {
                    long totalViews = stat.getViewCount();
                    long settlement = calculateSettlement(totalViews);
                    long adCount = getAdCountFromAdStats(stat.getVideoId().getId(), startOfMonth, endOfMonth);
                    long adRevenue = getAdRevenueFromAdStats(stat.getVideoId().getId(), startOfMonth, endOfMonth);
                    long total = Math.floorDiv(settlement + adRevenue, 1); // 두 개를 더한 후 1원 단위 이하 절사
                    totalSettlement.updateAndGet(v -> v + total);
                    return calcMapper.toDTO(stat, settlement, adRevenue, total, adCount);  // 광고 횟수 추가
                })
                .collect(Collectors.toList());

        return new SettlementResponseDTO(1L, totalSettlement.get(), startOfMonth.toString(), endOfMonth.toString(), videoCalcDTOs);
    }

    public SettlementResponseDTO getAllSettlement() {
        SettlementResponseDTO dailySettlement = getDailySettlement();
        SettlementResponseDTO weeklySettlement = getWeeklySettlement();
        SettlementResponseDTO monthlySettlement = getMonthlySettlement();

        long totalSettlement = dailySettlement.getTotalAmount() + weeklySettlement.getTotalAmount() + monthlySettlement.getTotalAmount();
        List<CalcDTO> allVideoCalcs = dailySettlement.getVideoCalculations();
        allVideoCalcs.addAll(weeklySettlement.getVideoCalculations());
        allVideoCalcs.addAll(monthlySettlement.getVideoCalculations());

        return new SettlementResponseDTO(1L, totalSettlement, LocalDate.now().atStartOfDay().toString(), LocalDate.now().atTime(LocalTime.MAX).toString(), allVideoCalcs);
    }
}
