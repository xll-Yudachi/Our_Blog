package com.ourblog.common.bean.user;

import com.ourblog.common.bean.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName Admin
 * @Description 后台管理者角色
 * @Author Yudachi
 * @Date 2021/2/3 9:41
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "tb_backstage_admin")
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
