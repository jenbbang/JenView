package org.sparta.jenview.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.videos.entity.VideoEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ad_statistics")
@IdClass(Ad_StatisticId.class)
public class Ad_StatisticsEntity {

    @Id
    @JoinColumn(name = "ad_id", nullable = false)
    private Long adId;

    @Id
    @JoinColumn(name = "video_id", nullable = false)
    private Long videoId;

    @Id
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @Column(name = "total_play_time", nullable = false)
    private long totalPlayTime;
}
