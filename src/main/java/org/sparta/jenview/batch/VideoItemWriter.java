package org.sparta.jenview.batch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class VideoItemWriter implements ItemWriter<VideoStatEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends VideoStatEntity> chunk) throws Exception {
        for (VideoStatEntity item : chunk.getItems()) {
            entityManager.merge(item); // 저장 또는 업데이트
        }
    }
}
