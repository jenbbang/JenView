package org.sparta.jenview.statistics.service;

import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.sparta.jenview.plays.repository.VideoPlayRepository;
import org.sparta.jenview.statistics.dto.VideoStatDTO;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.sparta.jenview.statistics.mapper.VideoStatMapper;
import org.sparta.jenview.statistics.repository.VideoStstRepository;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoStatService {

    private final VideoStstRepository videoStatRepository;
    private final VideoRepository videoRepository;
    private final VideoStatMapper videoStatMapper;
    private final VideoPlayRepository videoPlayRepository;

    @Autowired
    public VideoStatService(VideoPlayRepository videoPlayRepository, VideoStstRepository videoStatRepository, VideoRepository videoRepository, VideoStatMapper videoStatMapper) {
        this.videoStatRepository = videoStatRepository;
        this.videoRepository = videoRepository;
        this.videoStatMapper = videoStatMapper;
        this.videoPlayRepository = videoPlayRepository;
    }

    public List<VideoStatDTO> getTop5VideosByViews(LocalDate date, String period) {
        LocalDateTime startDate;
        LocalDateTime endDate;

        switch (period.toLowerCase()) {
            case "day":
                startDate = date.atStartOfDay();
                endDate = date.atTime(LocalTime.MAX);
                break;
            case "week":
                startDate = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
                endDate = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY)).atTime(LocalTime.MAX);
                break;
            case "month":
                startDate = date.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
                endDate = date.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        List<VideoStatEntity> videoStatEntities = videoStatRepository.findTop5ByCreatedAtBetweenOrderByViewCountDesc(startDate, endDate);
        return videoStatEntities.stream().map(videoStatMapper::toDTO).collect(Collectors.toList());
    }

    public List<VideoStatDTO> getTop5VideosByPlayTime(LocalDate date, String period) {
        LocalDateTime startDate;
        LocalDateTime endDate;

        switch (period.toLowerCase()) {
            case "day":
                startDate = date.atStartOfDay();
                endDate = date.atTime(LocalTime.MAX);
                break;
            case "week":
                startDate = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
                endDate = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY)).atTime(LocalTime.MAX);
                break;
            case "month":
                startDate = date.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
                endDate = date.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        List<VideoStatEntity> videoStatEntities = videoStatRepository.findTop5ByCreatedAtBetweenOrderByTotalPlayTimeDesc(startDate, endDate);
        return videoStatEntities.stream().map(videoStatMapper::toDTO).collect(Collectors.toList());
    }
    @Transactional
    public void createVideoStatistics(Long videoId) {
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + videoId));

        List<VideoPlayEntity> videoPlayEntities = videoPlayRepository.findByVideoEntity_Id(videoId);

        int totalViewCount = videoPlayEntities.size();
        long totalPlayTime = videoPlayEntities.stream().mapToLong(VideoPlayEntity::getPlayTime).sum();

        Optional<VideoStatEntity> latestStatOpt = Optional.ofNullable(videoStatRepository.findTopByVideoIdOrderByCreatedAtDesc(videoEntity));

        VideoStatEntity videoStatEntity;

        if (latestStatOpt.isPresent()) {
            videoStatEntity = latestStatOpt.get();
            videoStatEntity.setViewCount(totalViewCount);
            videoStatEntity.setTotalPlayTime(totalPlayTime);
        } else {
            videoStatEntity = new VideoStatEntity();
            videoStatEntity.setVideoId(videoEntity);
            videoStatEntity.setViewCount(totalViewCount);
            videoStatEntity.setTotalPlayTime(totalPlayTime);
            videoStatEntity.setCreatedAt(LocalDateTime.now());
        }

        videoStatRepository.save(videoStatEntity);
    }

    @Transactional
    public void createStatisticsForAllVideos() {
        List<VideoEntity> allVideos = videoRepository.findAll();
        for (VideoEntity video : allVideos) {
            createVideoStatistics(video.getId());
        }
    }
}