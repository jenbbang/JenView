package org.sparta.jenview.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.sparta.jenview.videos.entity.VideoEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "video_statistics")
@IdClass(VideoStatId.class)
public class VideoStatEntity {

    @Id
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", referencedColumnName = "id", insertable = false, updatable = false)
    private VideoEntity videoId;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @Column(name = "total_play_time", nullable = false)
    private long totalPlayTime;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
