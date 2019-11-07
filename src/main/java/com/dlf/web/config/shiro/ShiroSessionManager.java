package com.dlf.web.config.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

import static org.apache.shiro.web.servlet.ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE;

/**
 * 自定义session获取
 */
public class ShiroSessionManager extends DefaultWebSessionManager {

    private static final String USER_TICKET = "ut";

    ShiroSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String ut = WebUtils.toHttp(request).getHeader(USER_TICKET);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(ut)) {
            request.setAttribute(REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, ut);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return ut;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }
}
