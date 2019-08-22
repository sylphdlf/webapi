package com.dlf.web.enums;

public enum GlobalResultEnum {

    SUCCESS("0","成功"),
    FAIL("-1","失败"),
    LOG_OUT("-2","未登录"),
    CACHE("1","get data from cache"),
    MISSING_PARAMETER("-3", "丢失参数"),
    NULL_VALUE("-4", "没有数据"),
    UPDATE_FAIL("-5", "更新失败, 请稍后重试"),
    DATA_PERMISSION_REJECT("-6", "数据权限异常"),
    SUBMIT_REPEAT("-7", "重复提交"),
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