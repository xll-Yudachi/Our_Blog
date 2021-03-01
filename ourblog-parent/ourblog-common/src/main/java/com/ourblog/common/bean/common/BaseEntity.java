package com.ourblog.common.bean.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @ClassName BaseEntity
 * @Description 基础实体类，抽象出来创建时间和更新时间
 * @Author Yudachi
 * @Date 2021/1/21 11:25
 * @Version 1.0
 */

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;
    @LastModifiedDate
    @Column(name = "update_time")
   @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
}
