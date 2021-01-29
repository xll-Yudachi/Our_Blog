package com.ourblog.article.repository;

import com.ourblog.common.bean.article.ArticleContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface ArticleContentRepository extends JpaRepository<ArticleContent,Long> {

    public ArticleContent findArticleContentByAid(Long aid);
}
