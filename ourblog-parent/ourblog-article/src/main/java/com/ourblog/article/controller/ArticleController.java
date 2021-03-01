package com.ourblog.article.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.ourblog.article.service.ArticleService;
import com.ourblog.common.bean.article.Article;
import com.ourblog.common.bean.article.ArticleContent;
import com.ourblog.common.dto.article.ArticleDetailDto;
import com.ourblog.common.model.response.CommonCode;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.ResultCode;
import com.ourblog.common.model.response.articleCode.ArticleCode;
import com.ourblog.common.utils.OSSUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/findAll")
    public List<Map<Article, ArticleContent>> findAll() {
        return articleService.findAll();
    }

    /*
     * @Author yudachi
     * @Description 文章的逻辑删除
     * @Date 2021/1/29 15:44
     **/
    @DeleteMapping("/delete")
    public Result deleteArticleByIds(@RequestBody List<Long> ids) {
        List<Article> deleteArticles = articleService.deleteArticleByIds(ids);
        if (CollectionUtil.isEmpty(deleteArticles)) {
            return new Result(ArticleCode.DELETE_FAIL);
        } else {
            return new Result(ArticleCode.DELETE_SUCCESS);
        }
    }

    /*
     * @Author yudachi
     * @Description 更新文章
     * @Date 2021/1/29 16:09
     **/
    @PostMapping("/update")
    public Result updateArticleById(@RequestBody ArticleDetailDto articleDetail) {
        Article article = articleService.updateArticleById(articleDetail);
        if (ObjectUtil.isEmpty(article)) {
            return new Result(ArticleCode.UPDATE_FAIL);
        } else {
            return new Result(ArticleCode.UPDATE_SUCCESS);
        }
    }

    @GetMapping("/index")
    public Result getIndexArticle( @RequestParam("page") int page) {
        return new Result(articleService.getIndexArticle( page));
    }
    @GetMapping("/user")
    public Result getUserArticle(@RequestParam("uId") Long uid,@RequestParam("page")int page){
        return new Result(articleService.getUserArticle(uid,page));
    }

    @PostMapping("/new")
    public Result newArticle(@RequestBody ArticleDetailDto articleDetailDto) {
        if (articleDetailDto == null)
            return new Result(CommonCode.FAIL);
        Article article = articleService.newArticle(articleDetailDto);
        return article != null ? new Result(ArticleCode.UPDATE_SUCCESS, article) : new Result(ArticleCode.UPDATE_FAIL);
    }

    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file) {
        try {
            System.out.println(file.getName());
            String originalFilename = file.getOriginalFilename();
            System.out.println();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            return OSSUtils.simpleUpload(file.getInputStream(), ext);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(CommonCode.FAIL);
        }

    }

    @PostMapping("/searchByKeyword")
    public Result searchByKeyword(@RequestBody Map map) {
        if (map.get("type") != null && map.get("keyword") != null && map.get("page") != null)
            return new Result(articleService.searchByKeyword(map));
        return new Result(ArticleCode.SEARCH_FAIL);
    }
    
    @GetMapping("/getArticle")
    public Result getArticle(@RequestParam("id") Long id){
        return new Result(articleService.getArticle(id));
    }

}
