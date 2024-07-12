package org.sparta.jenview.ad.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Data
public class AdsId implements Serializable {
    private Long id;
    private Long videoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdsId that = (AdsId) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(videoId, that.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, videoId);
    }
}
