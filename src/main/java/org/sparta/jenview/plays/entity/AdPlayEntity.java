package org.sparta.jenview.plays.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.videos.entity.VideoEntity;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ad_plays")
@IdClass(AdPlayId.class)
public class AdPlayEntity {

    @Id
    @Column(name = "ad_id", nullable = false)
    private Long adId;

    @Id
    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", insertable = false, updatable = false)
    private AdEntity ad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private VideoEntity video;

    @Column(name = "play_time", nullable = false)
    private int playTime;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
}
