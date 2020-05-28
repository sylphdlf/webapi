package com.dlf.web.enums;

public enum GlobalEnum {

    REDIRECT_URL("redirect:/commNoAuth","免登陆统一入口"),
    ;

    private String code;
    private String msg;

    GlobalEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}