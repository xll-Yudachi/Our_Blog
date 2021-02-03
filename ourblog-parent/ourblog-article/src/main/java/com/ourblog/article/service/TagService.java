package com.ourblog.article.service;

import com.ourblog.common.bean.article.Tag;
import com.ourblog.common.dto.article.TagSearchDto;
import com.ourblog.common.model.response.PageResult;

import java.util.List;

/**
 * @ClassName TagService
 * @Description 文章标签服务
 * @Author Yudachi
 * @Date 2021/2/3 11:14
 * @Version 1.0
 */
public interface TagService {
    List<Tag> findAll(String tag);

    PageResult<Tag> findAllByPage(TagSearchDto tagSearchDto);

    Tag add(Tag tag);

    void delete(List<Long> ids);

    Tag update(Tag tag);
}
