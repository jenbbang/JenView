package org.sparta.jenview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication // Spring Boot 애플리케이션 진입점 및 자동 구성 활성화
@EnableJpaAuditing // JPA Auditing 활성화
public class JenViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(JenViewApplication.class, args);
    }

}
