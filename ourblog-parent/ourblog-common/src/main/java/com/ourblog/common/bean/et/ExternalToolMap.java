package com.ourblog.common.bean.et;

import com.ourblog.common.bean.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName ExternalToolMap
 * @Description 外部工具url映射实体类
 * @Author Yudachi
 * @Date 2021/2/4 9:08
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "tb_external_tool_map")
public class ExternalToolMap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    @Column(name = "is_delete")
    private Integer isDelete = 0;
}
