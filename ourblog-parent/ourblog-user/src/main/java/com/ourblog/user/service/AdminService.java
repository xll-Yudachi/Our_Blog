package com.ourblog.user.service;

import com.ourblog.common.bean.user.Admin;

/**
 * @ClassName AdminService
 * @Description 后台用户服务
 * @Author Yudachi
 * @Date 2021/2/3 9:45
 * @Version 1.0
 */
public interface AdminService {
    Admin login(Admin admin);
}
