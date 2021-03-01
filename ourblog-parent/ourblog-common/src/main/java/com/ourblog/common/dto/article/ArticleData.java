package com.ourblog.common.dto.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ArticleData
 * @Description 文章详情数据情况
 * @Author Yudachi
 * @Date 2021/1/29 15:35
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleData implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;
    private Integer watch;
}
