package com.ourblog.common.bean.article;

import com.ourblog.common.bean.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName Tag
 * @Description 文章类型实体类
 * @Author Yudachi
 * @Date 2021/2/3 11:10
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "tb_tag")
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
    @Column(name = "is_delete")
    private Integer isDelete = 0;
}
