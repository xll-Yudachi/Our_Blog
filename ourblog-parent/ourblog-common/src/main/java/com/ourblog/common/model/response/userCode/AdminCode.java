package com.ourblog.common.model.response.userCode;

import com.ourblog.common.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum AdminCode implements ResultCode {
    REGISTER_SUCCESS(true, 40000, "注册成功"),
    REGISTER_FAIL(false, 40001, "注册失败"),
    DUPLICATE_USERPHONE(false, 40002, "手机号码重复"),
    DUPLICATE_USERNAME(false, 40003, "用户名重复"),
    USERPHONE_SUCCESS(true, 40004, "手机号码正确"),
    USERNAME_SUCCESS(true, 40005, "用户名正确"),
    LOGIN_FAIL(false, 40006, "用户名或密码错误"),
    LOGIN_SUCCESS(true, 40007, "登录成功"),
    SEARCH_USERINFO_SUCCESS(true, 40008, "用户信息查询成功"),
    SEARCH_USERINFO_FAIL(false, 40009, "用户信息查询失败"),
    CODE_ERROR(false, 40010, "验证码错误"),
    UPDATE_USERINFO_SUCCESS(true, 40011, "用户信息更新成功"),
    UPDATE_USERINFO_FAIL(false, 40012, "用户信息更新失败");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private AdminCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
