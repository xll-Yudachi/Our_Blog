package com.ourblog.article.service;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import com.ourblog.common.dto.article.ArticleDetailDto;

import  java.util.*;

public interface ArticleService {
    List<Map<Article, ArticleContent>> findAll();

    List<Article> deleteArticleByIds(List<Long> ids);

    Article updateArticleById(ArticleDetailDto articleDetail);

    List<Article> getIndexArticle(Long uId,int page);
    boolean newArticle(ArticleDetailDto articleDetailDto);
}
