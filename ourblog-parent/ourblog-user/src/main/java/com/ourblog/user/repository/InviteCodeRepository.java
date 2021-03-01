package com.ourblog.user.repository;

import com.ourblog.common.bean.invite.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName InviteCodeRepository
 * @Description 邀请码持久层
 * @Author Yudachi
 * @Date 2021/2/3 10:12
 * @Version 1.0
 */
@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCode, Long> {
    InviteCode findInviteCodeByInviteCodeAndIsUse(String code, Integer isUse);
}
