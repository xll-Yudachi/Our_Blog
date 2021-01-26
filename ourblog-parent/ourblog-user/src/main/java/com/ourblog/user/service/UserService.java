package com.ourblog.user.service;

import com.ourblog.common.bean.user.User;
import com.ourblog.common.model.response.Result;

import java.util.List;

/**
 * @ClassName UserService
 * @Description 用户接口
 * @Author Yudachi
 * @Date 2021/1/21 10:38
 * @Version 1.0
 */
public interface UserService {
     List<User> findAll();
     User saveUser(User user);
    Result UserLogin(User user);

    public User findUser();
}
