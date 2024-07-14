package org.sparta.jenview.statistics.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoStatId implements Serializable {
    private Long videoId;
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoStatId that = (VideoStatId) o;
        return Objects.equals(videoId, that.videoId) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, createdAt);
    }
}
