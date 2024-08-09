package org.sparta.jenview.batch;

import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class VideoItemProcessor implements ItemProcessor<VideoPlayEntity, VideoStatEntity> {

    private ConcurrentMap<Long, Long> videoPlayTimeMap = new ConcurrentHashMap<>();
    private ConcurrentMap<Long, Integer> videoViewCountMap = new ConcurrentHashMap<>();

    @Override
    public VideoStatEntity process(VideoPlayEntity item) throws Exception {
        Long videoId = item.getVideoEntity().getId();
        videoPlayTimeMap.merge(videoId, item.getPlayTime(), Long::sum);
        videoViewCountMap.merge(videoId, 1, Integer::sum);

        VideoStatEntity videoStat = new VideoStatEntity();
        videoStat.setVideoId(item.getVideoEntity());
        videoStat.setTotalPlayTime(videoPlayTimeMap.get(videoId));
        videoStat.setViewCount(videoViewCountMap.get(videoId));
        videoStat.setCreatedAt(LocalDateTime.now());

        return videoStat;
    }
}