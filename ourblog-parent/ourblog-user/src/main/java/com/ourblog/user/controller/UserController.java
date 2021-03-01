package com.ourblog.user.controller;

import com.ourblog.common.bean.user.User;
import com.ourblog.common.model.response.CommonCode;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.userCode.UserCode;
import com.ourblog.user.repository.UserRepository;
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
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public Result findAll() {
        List<User> userList = userService.findAll();
        return new Result(UserCode.SEARCH_USERINFO_SUCCESS, userList);
    }

    @PostMapping("/save")
    public Result saveUser(@RequestBody User user) {
        User saveUser = userService.saveUser(user);
        return new Result(CommonCode.SUCCESS, saveUser);
    }

    @CrossOrigin
    @PostMapping("/modify")
    public Result updateUser(@RequestBody User user){
        boolean b = userService.updateUser(user);
        return b?new Result():new Result(CommonCode.FAIL);
    }

    @GetMapping("/findUser")
    public Result findUser(@RequestParam("uId") Long uId) {
        User user = userService.findUser(uId);
        if(user.getGithub()==null)
            user.setGithub("");
        return new Result(CommonCode.SUCCESS, user);
    }

    @PostMapping("/login")
    public Result UserLogin(@RequestBody User user) {
        return userService.UserLogin(user);
    }
}
