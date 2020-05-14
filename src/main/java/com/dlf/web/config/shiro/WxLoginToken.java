package com.dlf.web.config.shiro;

import com.dlf.web.dto.UserInfo;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;
@Data
public class WxLoginToken implements AuthenticationToken {

    private static final long serialVersionUID = 2307992506291029922L;

    private Object userInfo;

    private String username;

    private char[] password;

    public WxLoginToken(String username, char[] password, UserInfo userInfo) {
        this.username = username;
        this.password = password;
        this.userInfo = userInfo;
    }

    @Override
    public Object getPrincipal() {
        return getUsername();
    }

    @Override
    public Object getCredentials() {
        return getPassword();
    }
}
