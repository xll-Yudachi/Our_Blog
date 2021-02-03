package com.ourblog.common.model.response.articleCode;

import com.ourblog.common.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum ArticleCode implements ResultCode {
    DELETE_SUCCESS(true, 50000, "文章删除成功"),
    DELETE_FAIL(false, 50001, "文章删除失败"),
    UPDATE_SUCCESS(true, 50002, "文章更新成功"),
    UPDATE_FAIL(false, 50003, "文章更新失败"),
    SEARCH_TAG_SUCCESS(true, 50004, "文章标签查询成功"),
    SEARCH_TAG_FAIL(false, 50005, "暂无数据"),
    SAVE_TAG_SUCCESS(true, 50006, "文章标签保存成功"),
    SAVE_TAG_FAIL(false, 50007, "文章标签保存失败"),
    DELETE_TAG_SUCCESS(true, 50008, "文章标签删除成功"),
    DELETE_TAG_FAIL(false, 50009, "文章标签删除失败"),
    UPDATE_TAG_SUCCESS(true, 50010, "文章标签更新成功"),
    UPDATE_TAG_FAIL(false, 500011, "文章标签更新失败");

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
