package com.ourblog.user.controller;

import com.ourblog.common.dto.invite.InviteDto;
import com.ourblog.common.model.response.Result;
import com.ourblog.user.service.InviteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName InviteCodeController
 * @Description 邀请码注册控制层
 * @Author Yudachi
 * @Date 2021/2/3 9:57
 * @Version 1.0
 */
@RestController
@RequestMapping("/invite")
public class InviteCodeController {

    @Autowired
    private InviteCodeService inviteCodeService;

    @PostMapping("/register")
    public Result register(@RequestBody InviteDto inviteDto) {
        return inviteCodeService.register(inviteDto);
    }
}
