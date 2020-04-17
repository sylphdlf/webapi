package com.dlf.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WxUserDTO {

    private String openId;

    private String mobile;

    private String verifyCode;
}
