package org.sparta.jenview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class JenViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(JenViewApplication.class, args);
    }

}
