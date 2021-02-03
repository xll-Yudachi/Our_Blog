package com.ourblog.user;

import com.ourblog.common.exception.ExceptionCatch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @ClassName com.ourblog.user.UserApplication
 * @Description 用户模块启动类
 * @Author Yudachi
 * @Date 2021/1/21 10:45
 * @Version 1.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.ourblog.common.bean.user", "com.ourblog.common.bean.invite"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
    @Bean
    public ExceptionCatch exceptionCatch(){
        return  new ExceptionCatch();
    }
}
