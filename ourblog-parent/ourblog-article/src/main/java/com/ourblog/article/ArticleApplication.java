package com.ourblog.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @ClassName ArticleApplication
 * @Description TODO
 * @Author Yudachi
 * @Date 2021/1/28 11:01
 * @Version 1.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = "com.ourblog.common.bean")
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }
}
