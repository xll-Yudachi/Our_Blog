package com.ourblog.et.repository;

import com.ourblog.common.bean.article.Tag;
import com.ourblog.common.bean.et.ExternalToolMap;
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
public interface ETRepository extends JpaRepository<ExternalToolMap, Long> {

    @Query(value = "select * from tb_external_tool_map where name like %?1%", nativeQuery = true)
    List<ExternalToolMap> findExternalToolMapByNameLike(String name);
}
