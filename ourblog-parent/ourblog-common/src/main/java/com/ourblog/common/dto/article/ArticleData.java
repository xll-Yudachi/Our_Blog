package com.ourblog.common.dto.article;

import lombok.Data;

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
public class ArticleData implements Serializable {
    private Date time;
    private Integer watch;
}
