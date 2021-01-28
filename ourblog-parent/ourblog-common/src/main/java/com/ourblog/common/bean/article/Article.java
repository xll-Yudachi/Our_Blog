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
    @Column(name = "cid")
    private Long cId;
    @Column(name = "uid")
    private Long uId;
}
