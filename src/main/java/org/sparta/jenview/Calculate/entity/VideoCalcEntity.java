package org.sparta.jenview.Calculate.entity;

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
@Table(name = "video_calculate")
@IdClass(VideoCalcId.class)
public class VideoCalcEntity {

    @Id
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity videoId;

    @Column(name = "video_settlement", nullable = false)
    private long videoSettlement;
}
