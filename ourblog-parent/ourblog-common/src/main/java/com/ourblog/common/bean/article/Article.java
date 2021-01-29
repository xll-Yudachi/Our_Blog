package com.ourblog.common.bean.article;

import com.ourblog.common.bean.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_article")
@Builder
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "uid")
    private Long uId;
    @Column(name = "image")
    private String image;
    @Column(name = "tags")
    private String tags;
    @Column(name = "watch")
    private Integer watch = 0;
    @Column(name = "is_delete")
    private Integer isDelete = 0;
}
