package org.sparta.jenview.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomJobListener implements JobExecutionListener {

    public static long sequentialStartTime;
    public static long sequentialEndTime;
    public static long parallelStartTime;
    public static long parallelEndTime;
    public static int sequentialJobCount = 0;
    public static long sequentialTotalTime = 0;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String mode = jobExecution.getJobParameters().getString("mode");
        if ("순차".equals(mode)) {
            sequentialStartTime = System.currentTimeMillis();
        } else if ("병렬".equals(mode)) {
            parallelStartTime = System.currentTimeMillis();
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String mode = jobExecution.getJobParameters().getString("mode");
        if ("순차".equals(mode)) {
            sequentialEndTime = System.currentTimeMillis();
            long jobTime = sequentialEndTime - sequentialStartTime;
            sequentialTotalTime += jobTime;
            sequentialJobCount++;
        } else if ("병렬".equals(mode)) {
            parallelEndTime = System.currentTimeMillis();
        }
    }
}
