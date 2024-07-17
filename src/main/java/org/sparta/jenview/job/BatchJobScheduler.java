package org.sparta.jenview.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobScheduler {


    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job videoAndAdStatJob;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    public void runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(videoAndAdStatJob, jobParameters);
        } catch (Exception e) {
        }
    }
}
