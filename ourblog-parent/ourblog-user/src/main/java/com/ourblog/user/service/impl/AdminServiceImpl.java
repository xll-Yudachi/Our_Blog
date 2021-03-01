package com.ourblog.user.service.impl;

import com.ourblog.common.bean.user.Admin;
import com.ourblog.user.repository.AdminRepository;
import com.ourblog.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AdminServiceImpl
 * @Description 后台用户服务实现类
 * @Author Yudachi
 * @Date 2021/2/3 9:45
 * @Version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin login(Admin admin) {
        Admin dbAdmin = adminRepository.findAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        if (dbAdmin == null) {
            return null;
        } else {
            return dbAdmin;
        }
    }
}
