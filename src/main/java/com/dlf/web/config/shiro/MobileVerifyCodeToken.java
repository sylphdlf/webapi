package com.dlf.web.config.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

public class MobileVerifyCodeToken extends UsernamePasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken, Serializable {

    private String username;
    private String verifyCode;

    public MobileVerifyCodeToken(String username, String verifyCode) {
        this.username = username;
        this.verifyCode = verifyCode;
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
        return username;
    }

    @Override
    public Object getCredentials() {
        return "ok";
    }

    public String getUsername() {
        return username;
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
