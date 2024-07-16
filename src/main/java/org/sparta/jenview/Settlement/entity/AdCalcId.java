package org.sparta.jenview.Settlement.entity;

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
public class AdCalcId implements Serializable {
    private Long adId;
    private Long videoId;
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdCalcId that = (AdCalcId) o;
        return Objects.equals(adId, that.adId) &&
                Objects.equals(videoId, that.videoId) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adId, videoId, createdAt);
    }
}
