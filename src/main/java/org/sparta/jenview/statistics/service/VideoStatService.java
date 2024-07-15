package org.sparta.jenview.statistics.service;

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


    public void createVideoStatistics(Long videoId) {
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + videoId));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<VideoPlayEntity> videoPlayEntities = videoPlayRepository.findByVideoEntity_IdAndCreatedAtBetween(videoId, startOfDay, endOfDay);

        int totalViewCount = videoPlayEntities.size();
        long totalPlayTime = videoPlayEntities.stream().mapToLong(VideoPlayEntity::getLastPlayedTime).sum();

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
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        // 시청 기록이 있는 동영상만 조회
        List<Long> videoIdsWithPlays = videoPlayRepository.findDistinctVideoEntity_IdByCreatedAtBetween(startOfDay, endOfDay);

        for (Long videoId : videoIdsWithPlays) {
            createVideoStatistics(videoId);
        }
    }
}
