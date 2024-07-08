package org.sparta.jenview.ad.entity;

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
@Table(name = "ads")
@IdClass(AdsId.class)
public class AdEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Id
    @Column(name = "video_id")
    private Long videoId; // 외래 키로 사용될 videoId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private VideoEntity video;

    @Column(name = "start_time", nullable = false)
    private int startTime; // 광고가 시작되는 시간(초 단위)

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "ad_count", nullable = false)
    private int adCount;

    @Column(name = "play_time", nullable = false)
    private long playTime;

    @Column(name = "settled_today", nullable = false)
    private boolean settledToday;

    public void incrementViewCount() {
        this.adCount++;
    }
}
