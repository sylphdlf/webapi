package com.dlf.web.dto;

import java.io.Serializable;

public class BaseReqDTO<T> implements Serializable{

    private static final long serialVersionUID = 1L;
    //每次请求附带的requestId
    private String id;
    //接口名称
    private String action;
    //接口版本
    private String version;
    //设备类型
    private String deviceType;
    //访问时间
    private String requestTime;
    //用户ID
    private String userId;

    private Integer type;
    //图片验证码校验标识
    private Integer isCheckImgCode = 1;
    //参数
    private T data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsCheckImgCode() {
        return isCheckImgCode;
    }

    public void setIsCheckImgCode(Integer isCheckImgCode) {
        this.isCheckImgCode = isCheckImgCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
