package org.sparta.jenview.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.jenview.videos.entity.VideoEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "video_statistics")
@IdClass(Video_StatisticId.class)
public class Video_StatisticsEntity {

    @Id
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Id
    @Column(name = "video_id", nullable = false, updatable = false)
    private Long videoId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", referencedColumnName = "id", insertable = false, updatable = false)
    private VideoEntity video;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @Column(name = "total_play_time", nullable = false)
    private long totalPlayTime;
}
