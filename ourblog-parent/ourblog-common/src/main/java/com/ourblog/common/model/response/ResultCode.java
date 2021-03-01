package com.ourblog.common.model.response;

/**
 * @Author yudachi
 * @Description 返回码
 * 10000 -- 通用返回码
 * 20000 -- 用户中心返回码
 * 30000 -- 邀请码返回码
 * 40000 -- 后台用户返回码
 * 50000-50999 -- 文章系统返回码
 * 51000 -- 外部工具返回码
 * @Date 2021/1/21 10:22
 * @Version 1.0
 **/
public interface ResultCode {
    //操作是否成功
    boolean success();

    //操作代码
    int code();

    //提示信息
    String message();
}
