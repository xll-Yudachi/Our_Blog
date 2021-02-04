package com.ourblog.et.service;

import com.ourblog.common.bean.article.Tag;
import com.ourblog.common.bean.et.ExternalToolMap;
import com.ourblog.common.dto.article.TagSearchDto;
import com.ourblog.common.dto.et.ETSearchDto;
import com.ourblog.common.model.response.PageResult;

import java.util.List;

/**
 * @ClassName TagService
 * @Description 文章标签服务
 * @Author Yudachi
 * @Date 2021/2/3 11:14
 * @Version 1.0
 */
public interface ETService {
    List<ExternalToolMap> findAll(String name);

    PageResult<ExternalToolMap> findAllByPage(ETSearchDto etSearchDto);

    ExternalToolMap add(ExternalToolMap externalToolMap);

    void delete(List<Long> ids);

    ExternalToolMap update(ExternalToolMap externalToolMap);
}
