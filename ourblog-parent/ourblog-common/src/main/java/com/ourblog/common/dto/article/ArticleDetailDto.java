package com.ourblog.common.dto.article;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class ArticleDetailDto implements Serializable {
    private Long uId;
    private String authorName;
    private Long articleId;
    private String articleImg;
    private String articleTitle;
    private List<String> articleTags;
    private ArticleData articleData;
    private String mdArticle;
    private String htmlArticle;

    public void setuId(Long id){
        this.uId=id;
    }
    public Long getuId(){
        return this.uId;
    }
}
