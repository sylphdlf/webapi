package com.dlf.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class UserInfo implements Serializable{

    private static final long serialVersionUID = -991165482392593146L;

    private Long id;

    private String username;

    private String password;

}
