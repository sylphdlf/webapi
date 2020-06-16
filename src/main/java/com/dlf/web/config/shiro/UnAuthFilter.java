package com.dlf.web.config.shiro;

import com.alibaba.fastjson.JSON;
import com.dlf.web.enums.GlobalResultEnum;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UnAuthFilter extends UserFilter {

    @Override
    protected void redirectToLogin(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        // 返回json
        servletResponse.setContentType("application/json; charset=utf-8");
        Map<String, String> response = new HashMap<String, String>();
        response.put("code", GlobalResultEnum.LOG_OUT.getCode());
        response.put("msg", GlobalResultEnum.LOG_OUT.getCode());
        String json = JSON.toJSONString(response);
        servletResponse.getWriter().write(json);
    }
}
