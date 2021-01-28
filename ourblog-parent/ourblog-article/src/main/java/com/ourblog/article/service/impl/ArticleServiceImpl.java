package com.ourblog.article.service.impl;

import com.ourblog.article.repository.ArticleContentRepository;
import com.ourblog.article.repository.ArticleRepository;
import com.ourblog.article.service.ArticleService;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleContentRepository articleContentRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Override
    public List<Map<Article, ArticleContent>> findAll() {
        List<Article> all = articleRepository.findAll();
        List<Map<Article, ArticleContent>> list=new ArrayList<>();
        for(int i=0;i<all.size();i++){
            ArticleContent articleContent = articleContentRepository.findById(all.get(i).getCId()).get();
            Map<Article, ArticleContent> map= new HashMap<>();
            map.put(all.get(i),articleContent);
            list.add(map);
        }
        return list;
    }
}
