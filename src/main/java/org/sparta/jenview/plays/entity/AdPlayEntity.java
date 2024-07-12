package org.sparta.jenview.plays.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.videos.entity.VideoEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ad_plays")
//@IdClass(AdPlayId.class)
public class AdPlayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ad_id", referencedColumnName = "id")
    private AdEntity adId;

    @ManyToOne
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private VideoEntity videoId;

    @Column(name = "play_time", nullable = false)
    private int playTime;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


}
