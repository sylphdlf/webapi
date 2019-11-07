package com.dlf.web.enums;

import java.util.Enumeration;

public enum UserResultEnums implements Enumeration {

    TOKEN_ERROR("user_001","TOKEN_ERROR"),
    PERMISSION_DENIED("user_002","没有权限"),
    USERNAME_OR_PASSWORD_ERROR("user_003","用户名或密码错误"),
    LOGIN_FAIL("user_004", "登录失败"),
    ;

    private String code;
    private String msg;

    UserResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public boolean hasMoreElements() {
        return false;
    }

    @Override
    public Object nextElement() {
        return null;
    }
}