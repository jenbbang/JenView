package org.sparta.jenview.statistics.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoStatDTO {
    private String rank;  // rank 필드 추가
    private Long videoId;
    private String videoTitle;
    private int viewCount;
    private long totalPlayTime;
    private LocalDateTime createdAt;

    // 기본 생성자
    public VideoStatDTO() {
    }

    // 모든 필드를 초기화하는 생성자
    public VideoStatDTO(Long videoId, String videoTitle, int viewCount, long totalPlayTime, LocalDateTime createdAt, String rank) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.viewCount = viewCount;
        this.totalPlayTime = totalPlayTime;
        this.createdAt = createdAt;
        this.rank = rank;
    }

}
