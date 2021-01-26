package com.ourblog.user.controller;

import com.ourblog.common.bean.user.User;
import com.ourblog.common.model.response.CommonCode;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.userCode.UserCode;
import com.ourblog.user.service.feign.Oauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ourblog.user.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description 用户控制层
 * @Author Yudachi
 * @Date 2021/1/21 10:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Oauth2Service oauth2Service;

    @GetMapping("/findAll")
    public Result findAll(){
        List<User> userList = userService.findAll();
        return new Result(UserCode.SEARCH_USERINFO_SUCCESS,userList);
    }

    @PostMapping("/save")
    public Result saveUser(@RequestBody User user){
        User saveUser = userService.saveUser(user);
        return new Result(CommonCode.SUCCESS, saveUser);
    }

    @GetMapping("/findUser")
    public Result findUser(){
        User user = userService.findUser();
        return new Result(CommonCode.SUCCESS, user);
    }
    @PostMapping("/login")
    public Result UserLogin(@RequestBody User user){
        return userService.UserLogin(user);
    }
}
