package com.dlf.web.enums;

public enum GlobalResultEnum {

    SUCCESS("0","成功"),
    FAIL("-1","失败"),
    LOG_OUT("-2","未登录"),
    ;

    private String code;
    private String msg;

    GlobalResultEnum(String code, String msg) {
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