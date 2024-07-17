package org.sparta.jenview.job;

import lombok.extern.slf4j.Slf4j;
import org.sparta.jenview.Calculate.service.CalcService;
import org.sparta.jenview.statistics.service.AdStatService;
import org.sparta.jenview.statistics.service.VideoStatService;
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
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public Job videoAndAdStatJob(JobRepository jobRepository,
                                 @Qualifier("videoStatStep") Step videoStatStep,
                                 @Qualifier("adStatStep") Step adStatStep,
                                 @Qualifier("videoCalcStep") Step videoCalcStep,
                                 @Qualifier("adCalcStep") Step adCalcStep) {
        return new JobBuilder("videoAndAdStatJob", jobRepository)
                .start(videoStatStep)
                .next(adStatStep)
                .next(videoCalcStep)
                .next(adCalcStep)
                .build();
    }

    @Bean
    public Step videoStatStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, VideoStatService videoStatService) {
        return new StepBuilder("videoStatStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Starting videoStatStep");
                    videoStatService.createStatisticsForAllVideos();
                    log.info("Finished videoStatStep");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step adStatStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, AdStatService adStatService) {
        return new StepBuilder("adStatStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Starting adStatStep");
                    adStatService.createStatisticsForAllAds();
                    log.info("Finished adStatStep");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step videoCalcStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, CalcService calcService) {
        return new StepBuilder("videoCalcStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    calcService.calculateDailyVideoSettlement();
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step adCalcStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, CalcService calcService) {
        return new StepBuilder("adCalcStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Starting adCalcStep");
                    calcService.calculateDailyAdSettlement();
                    log.info("Finished adCalcStep");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }
}
