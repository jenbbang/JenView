package org.sparta.jenview.batch;

import lombok.extern.slf4j.Slf4j;
import org.sparta.jenview.Calculate.service.CalcService;
import org.sparta.jenview.statistics.service.AdStatService;
import org.sparta.jenview.statistics.service.VideoStatService;
import org.sparta.jenview.batch.CustomJobListener;
import org.sparta.jenview.batch.CustomStepListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


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
    public Step videoStatStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                              VideoStatService videoStatService, @Qualifier("batchTaskExecutor") TaskExecutor taskExecutor, CustomStepListener stepListener) {
        return new StepBuilder("videoStatStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step videoStatStep 시작");
                    videoStatService.createStatisticsForAllVideos();
                    log.info("Step videoStatStep 종료");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .listener(stepListener)
                .build();
    }

    // Uncomment and configure these steps as needed
    /*
    @Bean
    public Step adStatStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                           AdStatService adStatService, @Qualifier("batchTaskExecutor") TaskExecutor taskExecutor, CustomStepListener stepListener) {
        return new StepBuilder("adStatStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    taskExecutor.execute(() -> adStatService.createStatisticsForAllAds());
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
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
}
