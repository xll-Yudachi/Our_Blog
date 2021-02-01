package com.ourblog.article.repository;

import com.ourblog.common.bean.article.Article;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findArticlesByuIdAndIsDelete(Long uId, Integer isDelete, Pageable pageable);
}
