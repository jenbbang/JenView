package org.sparta.jenview.batch;

import lombok.extern.slf4j.Slf4j;
import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Slf4j
@Configuration
@EnableBatchProcessing
public class JobConfig {

    @Bean
    public Job videoAndAdStatJob(JobRepository jobRepository,
                                 @Qualifier("videoStatStep") Step videoStatStep,
                                 CustomJobListener jobListener) {
        return new JobBuilder("videoAndAdStatJob", jobRepository)
                .start(videoStatStep)
                .listener(jobListener)
                .build();
    }

    @Bean
    public Step videoStatStep(JobRepository jobRepository,
                              PlatformTransactionManager platformTransactionManager,
                              JdbcCursorItemReader<VideoPlayEntity> videoItemReader,
                              VideoItemProcessor videoItemProcessor,
                              VideoItemWriter videoItemWriter,
                              @Qualifier("batchTaskExecutor") TaskExecutor taskExecutor,
                              CustomStepListener stepListener) {

        return new StepBuilder("videoStatStep", jobRepository)
                .<VideoPlayEntity, VideoStatEntity>chunk(500, platformTransactionManager)
                .reader(videoItemReader)
                .processor(videoItemProcessor)
                .writer(videoItemWriter)
//                .taskExecutor(taskExecutor)
                .listener(stepListener)
                .build();
    }

    /*
    @Bean
public Step adStatStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                       JdbcCursorItemReader<AdEntity> adItemReader,
                       AdItemProcessor adItemProcessor,
                       AdItemWriter adItemWriter,
                       @Qualifier("batchTaskExecutor") TaskExecutor taskExecutor,
                       CustomStepListener stepListener) {

    return new StepBuilder("adStatStep", jobRepository)
            .<AdEntity, AdStatEntity>chunk(1000, platformTransactionManager)
            .reader(adItemReader)
            .processor(adItemProcessor)
            .writer(adItemWriter)
            .taskExecutor(taskExecutor)
            .listener(stepListener)
            .build();
}

    @Bean
    public Step videoCalcStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                              CalcService calcService, @Qualifier("batchTaskExecutor") TaskExecutor taskExecutor, CustomStepListener stepListener) {
        return new StepBuilder("videoCalcStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    taskExecutor.execute(() -> calcService.calculateDailyVideoSettlement());
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .listener(stepListener)
                .build();
    }

    @Bean
    public Step adCalcStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                           CalcService calcService, @Qualifier("batchTaskExecutor") TaskExecutor taskExecutor, CustomStepListener stepListener) {
        return new StepBuilder("adCalcStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    taskExecutor.execute(() -> calcService.calculateDailyAdSettlement());
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .listener(stepListener)
                .build();
    }
    */

    // ItemReader, ItemProcessor, ItemWriter를 Bean으로 정의해야 합니다.
//    @Bean
//    public VideoItemReaderConfig videoItemReader() {
//        return new VideoItemReaderConfig();
//    }

    @Bean
    public VideoItemProcessor videoItemProcessor() {
        return new VideoItemProcessor();
    }

    @Bean
    public VideoItemWriter videoItemWriter() {
        return new VideoItemWriter();
    }
    @Bean
    public JdbcPagingItemReader<VideoPlayEntity> videoItemReader(DataSource dataSource, PagingQueryProvider queryProvider) {
        return new JdbcPagingItemReaderBuilder<VideoPlayEntity>()
                .name("videoItemReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .pageSize(100)
                .rowMapper(new BeanPropertyRowMapper<>(VideoPlayEntity.class))
                .build();
    }
}


