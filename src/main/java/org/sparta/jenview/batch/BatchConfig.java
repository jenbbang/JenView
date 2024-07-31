package org.sparta.jenview.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchConfig {

    @Bean(name = "batchTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10); // 스레드 풀의 코어 스레드 수
        taskExecutor.setMaxPoolSize(20); // 최대 스레드 수
        taskExecutor.setQueueCapacity(25); // 큐 용량
        taskExecutor.setThreadNamePrefix("BatchThread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
