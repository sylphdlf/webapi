package com.dlf.web.config.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

public class RegisterAndLoginToken extends UsernamePasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken, Serializable {

    private String userId;
    private Integer type;

    public RegisterAndLoginToken(String userId, Integer type) {
        this.userId = userId;
        this.type = type;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return "ok";
    }

    public String getUserId() {
        return userId;
    }

    public Integer getType() {
        return type;
    }
}
