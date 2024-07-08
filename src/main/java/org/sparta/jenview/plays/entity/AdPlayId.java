package org.sparta.jenview.plays.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdPlayId implements Serializable {
    private Long id;
    private Long adId;
    private Long videoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdPlayId that = (AdPlayId) o;
        return Objects.equals(adId, that.adId) &&
                Objects.equals(videoId, that.videoId) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adId, videoId, id);
    }
}
