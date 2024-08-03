package org.sparta.jenview.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.sparta.jenview.batch.CustomJobListener.*;

@Component
public class Scheduler {

    private final JobLauncher jobLauncher;
    private final Job job1;
    private final Job job2;
    private final Job videoAndAdStatJob;
    //    private final TaskExecutor taskExecutor;
    private final JdbcTemplate jdbcTemplate;

    //    public Scheduler(JobLauncher jobLauncher, Job job1, Job job2, Job videoAndAdStatJob, @Qualifier("batchTaskExecutor") TaskExecutor taskExecutor, JdbcTemplate jdbcTemplate) {
    public Scheduler(JobLauncher jobLauncher, Job job1, Job job2, Job videoAndAdStatJob, JdbcTemplate jdbcTemplate) {
        this.jobLauncher = jobLauncher;
        this.job1 = job1;
        this.job2 = job2;
        this.videoAndAdStatJob = videoAndAdStatJob;
//        this.taskExecutor = taskExecutor;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedRate = 99999999)
    public void runBatchJobs() {
        try {
            System.out.println("\n[순차] 잡 실행 시작");

            sequentialTotalTime = 0; // 초기화
            sequentialJobCount = 0;  // 초기화
            JobExecution sequentialExecution1 = runJob(job1, "순차");
            Objects.requireNonNull(sequentialExecution1).getJobParameters();
            JobExecution sequentialExecution2 = runJob(job2, "순차");
            Objects.requireNonNull(sequentialExecution2).getJobParameters();
            JobExecution sequentialVideoAndAdStatExecution = runJob(videoAndAdStatJob, "순차");
            Objects.requireNonNull(sequentialVideoAndAdStatExecution).getJobParameters();

            while (sequentialExecution1.isRunning() || sequentialExecution2.isRunning() || sequentialVideoAndAdStatExecution.isRunning()) {
                Thread.sleep(100);
            }

            // 병렬 처리 주석 처리 시작
            /*
            System.out.println("[병렬] 잡 실행 시작\n");

            List<Runnable> tasks = new ArrayList<>();
            tasks.add(() -> runJob(job1, "병렬"));
            tasks.add(() -> runJob(job2, "병렬"));
            tasks.add(() -> runJob(videoAndAdStatJob, "병렬"));

            CountDownLatch latch = new CountDownLatch(tasks.size());

            for (Runnable task : tasks) {
                taskExecutor.execute(() -> {
                    task.run();
                    latch.countDown();
                });
            }

            latch.await();
            */

            printSummary();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JobExecution runJob(Job job, String mode) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("uuid", UUID.randomUUID().toString())
                    .addString("mode", mode)
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            while (jobExecution.isRunning()) {
                Thread.sleep(100);
            }
            return jobExecution;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printSummary() {
        System.out.println("\n=== 배치 Job 병렬처리 테스트 Summary ===");
        if (sequentialJobCount > 0) {
            System.out.println("순차 총 소요시간: " + sequentialTotalTime + " ms");
        }
        // 병렬 처리 요약 주석 처리 시작
        /*
        if (parallelStartTime > 0 && parallelEndTime > 0) {
            long parallelTotalTime = parallelEndTime - parallelStartTime;
            System.out.println("병렬 총 소요시간: " + parallelTotalTime + " ms");
        }
        System.out.println("순차와 병렬의 총 소요시간 차이: " + (parallelEndTime - parallelStartTime - sequentialTotalTime) * (-1) + " ms");
        */
    }
}
