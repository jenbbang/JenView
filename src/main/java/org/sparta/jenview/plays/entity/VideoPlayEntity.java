package org.sparta.jenview.plays.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.jenview.users.entity.UserEntity;
import org.sparta.jenview.videos.entity.VideoEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "video_plays")
public class VideoPlayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity videoEntity;

    @Column(name = "last_played_time", nullable = false)
    private int lastPlayedTime;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "stop_time", nullable = false)
    private Integer stopTime; // 시청이 중단된 시점

}
