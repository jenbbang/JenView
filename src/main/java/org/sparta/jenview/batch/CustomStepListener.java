package org.sparta.jenview.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Step " + stepExecution.getStepName() + " 시작");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Step " + stepExecution.getStepName() + " 종료");
        return stepExecution.getExitStatus();
    }
}
