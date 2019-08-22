package com.dlf.web.dto;


import com.dlf.web.enums.GlobalResultEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/4.
 */
public class GlobalResultDTO<T> implements Serializable{

    private static final long serialVersionUID = 3147080607075035747L;

    /**
     * 正确代码
     */
    private static final String SUCCESS_CODE = GlobalResultEnum.SUCCESS.getCode();

    /**
     * 错误代码
     */
    private static final String ERROR_CODE = GlobalResultEnum.FAIL.getCode();

    /**
     * 返回（正确/错误）代码
     */
    private String code;

    /**
     * 返回信息描述
     */
    private String msg;

    private String time;

    /**
     * 返回结果集
     */
    private T data;


    public GlobalResultDTO() {
        this.code = SUCCESS_CODE;
    }

    /**
     * 成功
     *
     * @param data
     */
    public GlobalResultDTO(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }

    /**
     * 成功
     *
     * @param msg
     * @param data
     */
    public GlobalResultDTO(String msg, T data) {
        this.code = SUCCESS_CODE;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 成功or失败
     *
     * @param msg
     * @param isSuccess
     */
    public GlobalResultDTO(String msg, boolean isSuccess) {
        if (!isSuccess) {
            this.code = ERROR_CODE;
        }
        this.msg = msg;
    }

    /**
     * 成功or失败
     *
     * @param msg
     * @param isSuccess
     */
    public GlobalResultDTO(String msg, T data, boolean isSuccess) {
        if (!isSuccess) {
            this.code = ERROR_CODE;
        } else {
            this.code = SUCCESS_CODE;
        }
        this.msg = msg;
        this.data = data;
    }


    public GlobalResultDTO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public GlobalResultDTO(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public boolean isSuccess() {
        return StringUtils.equalsIgnoreCase(SUCCESS_CODE, code);
    }


    public static GlobalResultDTO SUCCESS() {
        GlobalResultDTO resultDto = new GlobalResultDTO();
        resultDto.setCode(SUCCESS_CODE);
        return resultDto;
    }

    public static GlobalResultDTO SUCCESS(String message) {
        GlobalResultDTO resultDto = new GlobalResultDTO();
        resultDto.setCode(SUCCESS_CODE);
        resultDto.setMsg(message);
        return resultDto;
    }

    @SuppressWarnings("unchecked")
    public static GlobalResultDTO SUCCESS(Object obj) {
        GlobalResultDTO resultDto = new GlobalResultDTO();
        resultDto.setCode(SUCCESS_CODE);
        resultDto.setData(obj);
        return resultDto;
    }

    public static GlobalResultDTO FAIL() {
        return new GlobalResultDTO(GlobalResultEnum.FAIL.getMsg(), false);
    }
    public static GlobalResultDTO FAIL(String message) {
        return new GlobalResultDTO(message, false);
    }
    public static GlobalResultDTO FAIL(String code, String message) {
        return new GlobalResultDTO(code, message);
    }

}
