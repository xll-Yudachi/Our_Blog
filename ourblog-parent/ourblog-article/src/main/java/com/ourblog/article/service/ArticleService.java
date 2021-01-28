package com.ourblog.article.service;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;

import  java.util.*;

public interface ArticleService {
    List<Map<Article, ArticleContent>> findAll();
}
