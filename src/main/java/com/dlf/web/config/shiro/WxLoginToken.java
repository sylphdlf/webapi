package com.dlf.web.config.shiro;

import com.dlf.web.dto.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.UsernamePasswordToken;

@EqualsAndHashCode(callSuper = true)
@Data
public class WxLoginToken extends UsernamePasswordToken  {

    private static final long serialVersionUID = 2307992506291029922L;

    private Object userInfo;

    public WxLoginToken(String username, char[] password, UserInfo userInfo) {
        super.setUsername(username);
        super.setPassword(password);
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
