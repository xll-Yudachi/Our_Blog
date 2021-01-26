package com.ourblog.user.service.impl;

import com.ourblog.common.bean.user.User;
import com.ourblog.user.repository.UserRepository;
import com.ourblog.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        String userStr = request.getHeader("com/ourblog/user");
        User user = new User();
        user.setUsername(userStr);
        return user;
    }
}
