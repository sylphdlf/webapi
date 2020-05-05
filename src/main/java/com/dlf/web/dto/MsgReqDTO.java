package com.dlf.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MsgReqDTO implements Serializable {
    private static final long serialVersionUID = -8853539590069080287L;
    //手机
    private String mobile;
    //内容
    private String content;

    private String verifyCode;

    private String redisKey;
}
