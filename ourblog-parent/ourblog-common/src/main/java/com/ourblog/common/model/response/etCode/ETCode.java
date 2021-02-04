package com.ourblog.common.model.response.etCode;

import com.ourblog.common.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum ETCode implements ResultCode {
    DELETE_SUCCESS(true, 51000, "外部工具删除成功"),
    DELETE_FAIL(false, 51001, "外部工具删除失败"),
    UPDATE_SUCCESS(true, 51002, "外部工具更新成功"),
    UPDATE_FAIL(false, 51003, "外部工具更新失败"),
    SEARCH_SUCCESS(true, 51004, "外部工具查询成功"),
    SEARCH_FAIL(false, 51005, "暂无数据"),
    SAVE_SUCCESS(true, 51006, "外部工具保存成功"),
    SAVE_FAIL(false, 51007, "外部工具标签保存失败");


    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private ETCode(boolean success, int code, String message) {
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
