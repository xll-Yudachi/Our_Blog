package com.ourblog.et;

import com.ourblog.common.exception.ExceptionCatch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @ClassName ETApplication
 * @Description 外部工具启动类
 * @Author Yudachi
 * @Date 2021/2/4 9:05
 * @Version 1.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.ourblog.common.bean.et"})
public class ETApplication {
    public static void main(String[] args) {
        SpringApplication.run(ETApplication.class, args);
    }

    @Bean
    public ExceptionCatch exceptionCatch() {
        return new ExceptionCatch();
    }
}
