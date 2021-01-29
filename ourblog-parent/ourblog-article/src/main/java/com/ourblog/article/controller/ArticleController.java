package com.ourblog.article.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ourblog.article.service.ArticleService;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import com.ourblog.common.dto.article.ArticleDetailDto;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.articleCode.ArticleCode;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /*
     * @Author yudachi
     * @Description 文章的逻辑删除
     * @Date 2021/1/29 15:44
     **/
    @DeleteMapping("/delete")
    public Result deleteArticleByIds(@RequestBody List<Long> ids){
        List<Article> deleteArticles = articleService.deleteArticleByIds(ids);
        if (CollectionUtil.isEmpty(deleteArticles)){
            return new Result(ArticleCode.DELETE_FAIL);
        }else{
            return new Result(ArticleCode.DELETE_SUCCESS);
        }
    }

    /*
     * @Author yudachi
     * @Description 更新文章
     * @Date 2021/1/29 16:09
     **/
    @PostMapping("/update")
    public Result updateArticleById(@RequestBody ArticleDetailDto articleDetail){
        Article article = articleService.updateArticleById(articleDetail);
        if (ObjectUtil.isEmpty(article)){
            return new Result(ArticleCode.UPDATE_FAIL);
        }else{
            return new Result(ArticleCode.UPDATE_SUCCESS);
        }
    }
}
