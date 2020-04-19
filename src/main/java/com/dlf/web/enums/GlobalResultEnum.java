package com.dlf.web.enums;

public enum GlobalResultEnum {

    SUCCESS("0","成功"),
    FAIL("-1","失败"),
    LOG_OUT("-2","未登录"),
    REQUEST_KEY_NULL("-3", "非法访问"),
    REQUEST_KEY_INVALID("-4", "请刷新下页面"),
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