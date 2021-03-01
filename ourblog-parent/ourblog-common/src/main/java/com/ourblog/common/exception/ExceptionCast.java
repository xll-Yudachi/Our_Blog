package com.ourblog.common.exception;


import com.ourblog.common.model.response.ResultCode;
import com.ourblog.common.model.response.userCode.UserCode;

/**
 * @Author yudachi
 * @Description 异常抛出类
 * @Date 2021/1/21 10:21
 * @Version 1.0
 **/
public class ExceptionCast {


    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
