package com.ourblog.user.service.impl;


import com.ourblog.common.bean.invite.InviteCode;
import com.ourblog.common.bean.user.Admin;
import com.ourblog.common.bean.user.User;
import com.ourblog.common.constant.PasswordConstant;
import com.ourblog.common.dto.invite.InviteDto;
import com.ourblog.common.enums.InviteType;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.inviteCode.InviteCodes;
import com.ourblog.user.repository.AdminRepository;
import com.ourblog.user.repository.InviteCodeRepository;
import com.ourblog.user.repository.UserRepository;
import com.ourblog.user.service.InviteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName InviteCodeServiceImpl
 * @Description 邀请码注册服务实现类
 * @Author Yudachi
 * @Date 2021/2/3 9:59
 * @Version 1.0
 */
@Service
public class InviteCodeServiceImpl implements InviteCodeService {
    @Autowired
    private InviteCodeRepository inviteCodeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Result register(InviteDto inviteDto) {
        boolean isUser = true;
        if (!InviteType.USER.getName().equals(inviteDto.getType())){
            isUser = false;
        }
        InviteCode inviteCode = inviteCodeRepository.findInviteCodeByInviteCodeAndIsUse(inviteDto.getCode(), 1);
        if (inviteCode != null){
            // 邀请码可用
            if (isUser){
                // 用户注册
                User user = new User();
                user.setUsername(inviteDto.getUsername());
                user.setPassword(PasswordConstant.USERDEFAULTPASSWORD);
                userRepository.save(user);
            }else{
                //后台用户注册
                Admin admin = new Admin();
                admin.setUsername(inviteDto.getUsername());
                admin.setPassword(PasswordConstant.ADMINDEFAULTPASSWORD);
                adminRepository.save(admin);
            }
        }else{
            return new Result(InviteCodes.REGISTER_FAIL);
        }
        return new Result(InviteCodes.REGISTER_SUCCESS);
    }
}
