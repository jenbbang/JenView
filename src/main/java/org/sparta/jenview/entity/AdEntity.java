package org.sparta.jenview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ad")
public class AdEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity video;

    @Column(name = "start_time", nullable = false)
    private int startTime; // 광고가 시작되는 시간(초 단위)

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "duration", nullable = false)
    private int duration;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "ad_count", nullable = false)
    private int adCount;

    @Column(name = "play_time", nullable = false)
    private long playTime;

    public void incrementViewCount() {
        this.adCount++;
    }
}


