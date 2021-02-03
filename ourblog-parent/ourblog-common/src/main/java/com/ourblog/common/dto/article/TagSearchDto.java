package com.ourblog.common.dto.article;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TagSearchDto
 * @Description 文章标签查询传输类
 * @Author Yudachi
 * @Date 2021/2/3 11:25
 * @Version 1.0
 */
@Data
public class TagSearchDto implements Serializable {
    private Integer page;
    private Integer size;
    private String query;
}
