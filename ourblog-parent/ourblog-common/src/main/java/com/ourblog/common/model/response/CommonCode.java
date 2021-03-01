package com.ourblog.common.model.response;

import lombok.ToString;

@ToString
public enum CommonCode implements ResultCode {
    SUCCESS(true, 10001, "操作成功！"),
    FAIL(false, 10002, "操作失败！"),
    INVALID_PARAM(false, 10003, "非法参数！"),
    UNAUTHENTICATED(false, 10004, "此操作需要登陆系统！"),
    UNAUTHORISE(false, 10005, "权限不足，无权操作！"),
    SERVER_ERROR(false, 10006, "抱歉，系统繁忙，请稍后重试！"),
    COMMON_AUTHORIZED_FAILURE(false, 10007, "通用鉴权失败");
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private CommonCode(boolean success, int code, String message) {
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
