package com.ourblog.common.enums;

import lombok.Data;

/**
 * @ClassName InviteType
 * @Description 邀请码类型枚举类
 * @Author Yudachi
 * @Date 2021/2/3 10:04
 * @Version 1.0
 */
public enum InviteType {
    USER(1, "user"),
    ADMIN(2, "admin");

    private int type;
    private String name;

    private InviteType(int type, String name) {
        this.type = type;
        this.name = name();
    }

    public int gettype() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void settype(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
