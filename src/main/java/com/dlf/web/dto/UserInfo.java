package com.dlf.web.dto;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -8757295525617486784L;
    private Long id;

    private String username;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
