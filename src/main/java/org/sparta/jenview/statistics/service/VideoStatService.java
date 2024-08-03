package org.sparta.jenview.statistics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.sparta.jenview.plays.repository.VideoPlayRepository;
import org.sparta.jenview.statistics.dto.VideoStatDTO;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.sparta.jenview.statistics.mapper.VideoStatMapper;
import org.sparta.jenview.statistics.repository.VideoStatRepository;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.sparta.jenview.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



@Service
public class VideoStatService {

    private static final Logger logger = LoggerFactory.getLogger(VideoStatService.class);

    private final VideoStatRepository videoStatRepository;
    private final VideoRepository videoRepository;
    private final VideoPlayRepository videoPlayRepository;
    private final VideoStatMapper videoStatMapper;

    @Autowired
    public VideoStatService(VideoStatMapper videoStatMapper, VideoPlayRepository videoPlayRepository, VideoStatRepository videoStatRepository, VideoRepository videoRepository) {
        this.videoStatRepository = videoStatRepository;
        this.videoRepository = videoRepository;
        this.videoPlayRepository = videoPlayRepository;
        this.videoStatMapper = videoStatMapper;
    }

    public List<VideoStatDTO> getTop5ByViewCount(LocalDateTime start, LocalDateTime end) {
        Pageable pageable = PageRequest.of(0, 5);
        List<VideoStatEntity> entities = videoStatRepository.findTop5ByViewCount(start, end, pageable);

        return IntStream.range(0, entities.size())
                .mapToObj(i -> videoStatMapper.toDTO(entities.get(i), "TOP " + (i + 1)))
                .collect(Collectors.toList());
    }

    public List<VideoStatDTO> getTop5ByTotalPlayTime(LocalDateTime start, LocalDateTime end) {
        Pageable pageable = PageRequest.of(0, 5);
        List<VideoStatEntity> entities = videoStatRepository.findTop5ByTotalPlayTime(start, end, pageable);

        return IntStream.range(0, entities.size())
                .mapToObj(i -> videoStatMapper.toDTO(entities.get(i), "TOP " + (i + 1)))
                .collect(Collectors.toList());
    }


    @Transactional
    public void createStatisticsForAllVideos() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        logger.info("Fetching video IDs with plays between {} and {}", startOfDay, endOfDay);

        long startQueryTime = System.currentTimeMillis();
        List<Long> videoIdsWithPlays = videoPlayRepository.findDistinctVideoEntity_IdByCreatedAtBetween(startOfDay, endOfDay);
        long endQueryTime = System.currentTimeMillis();
        logger.info("Query for video IDs took {} ms", (endQueryTime - startQueryTime)); // 쿼리 실행 시간 로깅

        logger.info("Found {} video IDs with plays", videoIdsWithPlays.size()); // 로깅 추가

        if (videoIdsWithPlays.isEmpty()) {
            logger.warn("No video plays found for today."); // 데이터가 없을 경우 경고 로그
            return; // 데이터가 없을 경우 바로 리턴
        }

        for (Long videoId : videoIdsWithPlays) {
            logger.info("Processing video ID: {}", videoId); // 개별 비디오 ID 로깅 추가
            createVideoStatistics(videoId);
        }
    }

    public void createVideoStatistics(Long videoId) {
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + videoId));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        long startQueryTime = System.currentTimeMillis();
        List<VideoPlayEntity> videoPlayEntities = videoPlayRepository.findByVideoEntity_IdAndCreatedAtBetween(videoId, startOfDay, endOfDay);
        long endQueryTime = System.currentTimeMillis();
        logger.info("Query for video plays for video ID {} took {} ms", videoId, (endQueryTime - startQueryTime)); // 쿼리 실행 시간 로깅

        logger.info("Processing video ID: {}. Found {} play records", videoId, videoPlayEntities.size()); // 로깅 추가

        if(videoPlayEntities.isEmpty()) {
            logger.warn("No play records found for video ID: {}", videoId); // 시청 기록이 없을 경우 경고 로그
            return; // 시청 기록이 없을 경우 바로 리턴
        }

        int totalViewCount = videoPlayEntities.size();
        long totalPlayTime = videoPlayEntities.stream().mapToLong(VideoPlayEntity::getLastPlayedTime).sum();

        logger.info("Video ID: {}, Total Views: {}, Total Play Time: {}", videoId, totalViewCount, totalPlayTime); // 로깅 추가

        // 매번 새로운 통계 데이터 생성
        VideoStatEntity videoStatEntity = new VideoStatEntity();
        videoStatEntity.setVideoId(videoEntity);
        videoStatEntity.setViewCount(totalViewCount);
        videoStatEntity.setTotalPlayTime(totalPlayTime);
        videoStatEntity.setCreatedAt(LocalDateTime.now());

        videoStatRepository.save(videoStatEntity);
        logger.info("Saved VideoStatEntity: {}", videoStatEntity); // 로깅 추가
    }
}
