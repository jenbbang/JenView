package org.sparta.jenview.batch;

import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Configuration
public class VideoItemReaderConfig {

    @Bean
    @StepScope
    public JdbcPagingItemReader<VideoPlayEntity> videoItemReader(DataSource dataSource) {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("limit", 500);

        return new JdbcPagingItemReaderBuilder<VideoPlayEntity>()
                .name("videoItemReader")
                .dataSource(dataSource)
                .queryProvider(createQueryProvider())
                .parameterValues(parameterValues)
                .pageSize(500)
                .rowMapper(new BeanPropertyRowMapper<>(VideoPlayEntity.class))
                .build();
    }

    @Bean
    public PagingQueryProvider createQueryProvider() {
        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("SELECT *");
        queryProvider.setFromClause("FROM video_plays");
        // Set sort keys
        Map<String, Order> sortKeys = new TreeMap<>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        return queryProvider;
    }
}
