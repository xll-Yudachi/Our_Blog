package com.ourblog.common.dto.article;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ArticleTags
 * @Description 文章分类
 * @Author Yudachi
 * @Date 2021/2/4 11:43
 * @Version 1.0
 */
@Data
public class ArticleTags implements Serializable {
    private String category;
    private List<String> tags;
}
