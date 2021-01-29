package com.ourblog.common.dto.article;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class ArticleDetailDto implements Serializable {
    private Long articleId;
    private String articleImg;
    private String articleTitle;
    private List<String> articleTags;
    private ArticleData articleData;
    private String mdArticle;
    private String htmlArticle;
}
