package com.ourblog.user.service.impl;

import com.ourblog.common.bean.user.User;
import com.ourblog.common.exception.CustomException;
import com.ourblog.common.exception.ExceptionCast;
import com.ourblog.common.model.response.Result;
import com.ourblog.common.model.response.ResultCode;
import com.ourblog.common.model.response.userCode.UserCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.ourblog.user.repository.UserRepository;
import com.ourblog.user.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName UserServiceImpl
 * @Description 用户接口实现类
 * @Author Yudachi
 * @Date 2021/1/21 10:39
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user){
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public User findUser() {
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        User user = new User();
        user.setUsername(userStr);
        return user;
    }

    @Override
    public Result UserLogin(User user) {
        User user1 = userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        //Optional.ofNullable(user1).orElseThrow(()-> new CustomException(UserCode.LOGIN_FAIL));
        return user1==null ? new Result(UserCode.LOGIN_FAIL):new Result(user1.getId());
    }
}
