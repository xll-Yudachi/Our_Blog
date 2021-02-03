package com.ourblog.article.repository;

import com.ourblog.common.bean.article.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName TagRepository
 * @Description 文章标签持久层
 * @Author Yudachi
 * @Date 2021/2/3 11:11
 * @Version 1.0
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "select * from tb_tag where tag like %?1%", nativeQuery = true)
    List<Tag> findTagsByTagLike(String tag);
}
