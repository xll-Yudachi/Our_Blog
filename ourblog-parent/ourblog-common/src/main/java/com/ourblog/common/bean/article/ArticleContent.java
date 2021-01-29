package com.ourblog.common.bean.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "article_content")
@Builder
public class ArticleContent  {
    @Id
    private Long id;
    private String content;
    private Long aid;
}
