package com.ourblog.user.repository;

import com.ourblog.common.bean.user.Admin;
import com.ourblog.common.bean.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRepository
 * @Description 后台用户持久层
 * @Author Yudachi
 * @Date 2021/1/21 10:37
 * @Version 1.0
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findAdminByUsernameAndPassword(String username, String password);
}
