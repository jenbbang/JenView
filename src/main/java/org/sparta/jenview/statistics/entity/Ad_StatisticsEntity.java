package org.sparta.jenview.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.videos.entity.VideoEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ad_statistics")
@IdClass(Ad_StatisticId.class)
public class Ad_StatisticsEntity {

    @Id
    @Column(name = "ad_id", nullable = false)
    private Long adId;

    @Id
    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Id
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("adId")
    @JoinColumn(name = "ad_id", nullable = false, insertable = false, updatable = false)
    private AdEntity ad;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    @JoinColumn(name = "video_id", nullable = false, insertable = false, updatable = false)
    private VideoEntity video;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @Column(name = "total_play_time", nullable = false)
    private long totalPlayTime;
}
