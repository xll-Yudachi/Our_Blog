package com.ourblog.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.ourblog.article.repository.ArticleContentRepository;
import com.ourblog.article.repository.ArticleRepository;
import com.ourblog.article.service.ArticleService;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import com.ourblog.common.dto.article.ArticleData;
import com.ourblog.common.dto.article.ArticleDetailDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
       /* for(int i=0;i<all.size();i++){
            ArticleContent articleContent = articleContentRepository.findById(all.get(i).getCId()).get();
            Map<Article, ArticleContent> map= new HashMap<>();
            map.put(all.get(i),articleContent);
            list.add(map);
        }*/
        return list;
    }

    @Override
    public List<Article> deleteArticleByIds(List<Long> ids) {
        List<Article> articleList = articleRepository.findAllById(ids);
        List<Article> deleteArticleList = articleList.stream().map(article -> {
            article.setIsDelete(1);
            return article;
        }).collect(Collectors.toList());
        List<Article> saveAll = articleRepository.saveAll(deleteArticleList);
        return saveAll;
    }

    @Override
    public Article updateArticleById(ArticleDetailDto articleDetail) {
        //数据库的Article
        Article dbArticle = articleRepository.findById(articleDetail.getArticleId()).get();
        if (dbArticle == null){
            return null;
        }
        // 更新后的Article
        dbArticle = articleDetailCopyPropertiesToArticle(dbArticle, articleDetail);
        // 数据库的ArticleContent
        ArticleContent dbArticleContent = articleContentRepository.findArticleContentByAid(articleDetail.getArticleId());
        // 更新ArticleContent
        if (StringUtils.isEmpty(articleDetail.getHtmlArticle())){
            if (StringUtils.isEmpty(articleDetail.getMdArticle())){
                dbArticleContent.setContent("");
            }else{
                dbArticleContent.setContent(articleDetail.getMdArticle());
            }
        }else{
            dbArticleContent.setContent(articleDetail.getHtmlArticle());
        }

        // 回存数据库
        Article save = articleRepository.save(dbArticle);
        articleContentRepository.save(dbArticleContent);

        return save;
    }

    /**
     * @Author yudachi
     * @Description 文章详情属性填充到文章中
     * @Date 2021/1/29 16:16
     **/
    private Article articleDetailCopyPropertiesToArticle(Article article, ArticleDetailDto articleDetail){
        article.setImage(articleDetail.getArticleImg());
        article.setTitle(articleDetail.getArticleTitle());
        article.setTags(JSON.toJSONString(articleDetail.getArticleTags()));
        article.setUpdateTime(articleDetail.getArticleData().getTime());
        article.setWatch(articleDetail.getArticleData().getWatch());
        return article;
    }


}
