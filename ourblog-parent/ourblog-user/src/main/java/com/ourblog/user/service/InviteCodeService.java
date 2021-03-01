package com.ourblog.user.service;

import com.ourblog.common.dto.invite.InviteDto;
import com.ourblog.common.model.response.Result;

/**
 * @ClassName InviteCodeService
 * @Description 邀请码注册服务
 * @Author Yudachi
 * @Date 2021/2/3 9:58
 * @Version 1.0
 */
public interface InviteCodeService {
    Result register(InviteDto inviteDto);
}
