package com.ourblog.common.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleEsSaveDto {
    private Long id;
    private String title;
    private String articleImg;
    private String content;
    private String authorName;
    private Long authorId;
    private String authorPic;
    private String tags;
    private Integer watch;
    private Integer isDelete;
    private Date updateTime;
}
