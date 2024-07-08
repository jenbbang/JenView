package org.sparta.jenview.settlement.entity;

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
@Table(name = "settlement")
@IdClass(Video_SettlementId.class)
public class Video_SettlementEntity {

    @Id
    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Id
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    @JoinColumn(name = "video_id", nullable = false, insertable = false, updatable = false)
    private VideoEntity videoEntity;

    @Column(name = "video_settlement", nullable = false)
    private double videoSettlement;
}
