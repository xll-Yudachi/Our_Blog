package com.ourblog.common.model.response.inviteCode;

import com.ourblog.common.model.response.ResultCode;

/**
 * @ClassName InviteCode
 * @Description 邀请码返回码
 * @Author Yudachi
 * @Date 2021/2/3 10:18
 * @Version 1.0
 */
public enum InviteCodes implements ResultCode{
    REGISTER_SUCCESS(true, 30000, "邀请码注册成功"),
    REGISTER_FAIL(false, 30001, "邀请码注册失败");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private InviteCodes(boolean success, int code, String message) {
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


