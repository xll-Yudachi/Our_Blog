package com.ourblog.common.model.response.articleCode;

import com.ourblog.common.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum ArticleCode implements ResultCode {
    DELETE_SUCCESS(true, 50000, "文章删除成功"),
    DELETE_FAIL(false, 50001, "文章删除失败"),
    UPDATE_SUCCESS(true, 50002, "文章更新成功"),
    UPDATE_FAIL(false, 50003, "文章更新失败");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private ArticleCode(boolean success, int code, String message) {
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
