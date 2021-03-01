package com.ourblog.article.service;

import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import com.ourblog.common.dto.article.ArticleDetailDto;
import com.ourblog.common.dto.article.ArticleIndexDto;

import java.util.*;

public interface ArticleService {
    List<Map<Article, ArticleContent>> findAll();

    List<Article> deleteArticleByIds(List<Long> ids);

    Article updateArticleById(ArticleDetailDto articleDetail);

    List<ArticleIndexDto> getIndexArticle( int page);
    List<ArticleIndexDto> getUserArticle( Long uid,int page);

    Article newArticle(ArticleDetailDto articleDetailDto);

    List searchByKeyword(Map map);

    ArticleDetailDto getArticle(Long id);
}
