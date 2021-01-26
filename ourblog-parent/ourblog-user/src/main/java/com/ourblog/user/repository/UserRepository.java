package com.ourblog.user.repository;

import com.ourblog.common.bean.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRepository
 * @Description 用户持久层
 * @Author Yudachi
 * @Date 2021/1/21 10:37
 * @Version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
