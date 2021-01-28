package com.ourblog.article.controller;

import com.ourblog.article.service.ArticleService;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import  java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @GetMapping("/findAll")
    public List<Map<Article, ArticleContent>> findAll(){
        return  articleService.findAll();
    }
}
