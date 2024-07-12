package org.sparta.jenview.ad.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ads")
//@IdClass(AdsId.class)
@EntityListeners(AuditingEntityListener.class)
public class AdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoEntity video; // 외래 키로 사용될 videoId

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "ad_count", nullable = false)
    private int adCount;

    @Column(name = "start_time", nullable = false)
    private int startTime; // 광고가 시작되는 시간(초 단위)

    @Column(name = "settled_today", nullable = false)
    private boolean settledToday;

    public void incrementViewCount() {
        this.adCount++;
    }

    public void setStartTime(int startTime) {
    }

}