package com.ourblog.user.controller;

import com.ourblog.common.bean.user.Admin;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.userCode.AdminCode;
import com.ourblog.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Description 后台管理主用户
 * @Author Yudachi
 * @Date 2021/2/3 9:39
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        Admin login = adminService.login(admin);
        if (login == null){
            return new Result(AdminCode.LOGIN_FAIL);
        }else{
            return new Result(AdminCode.LOGIN_SUCCESS, login.getId());
        }
    }
}
