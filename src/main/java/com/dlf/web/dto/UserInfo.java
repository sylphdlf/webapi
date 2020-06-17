package com.dlf.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserInfo implements Serializable{


    private static final long serialVersionUID = 3486569872589078064L;
    private Long id;

    private String username;

    private String password;

    private Integer isAdmin;

    private String ip;

    private String lastIp;

    private Date updateTime;

}
