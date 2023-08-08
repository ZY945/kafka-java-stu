package io.zy945.study.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 伍六七
 * @date 2023/8/8 10:30
 */
@SpringBootApplication
public class KafkaDemoApplication {
    public static void main(String[] args) {
        new SpringApplication(KafkaDemoApplication.class)
                .run(args);
    }
}
