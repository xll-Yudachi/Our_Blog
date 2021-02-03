package com.ourblog.common.bean.invite;

import com.ourblog.common.bean.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName InviteCode
 * @Description TODO
 * @Author Yudachi
 * @Date 2021/2/3 10:07
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "tb_invite_code")
public class InviteCode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invite_code")
    private String inviteCode;
    @Column(name = "is_use")
    private Integer isUse;
}
