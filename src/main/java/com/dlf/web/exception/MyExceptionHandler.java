package com.dlf.web.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.dlf.web.enums.UserResultEnums;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        String CODE = "code";
        String MSG = "msg";
        if (ex instanceof UnauthenticatedException) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
            attributes.put(CODE, UserResultEnums.TOKEN_ERROR.getCode());
            attributes.put(MSG, UserResultEnums.TOKEN_ERROR.getMsg());
        } else if (ex instanceof UnauthorizedException) {
            attributes.put(CODE, UserResultEnums.PERMISSION_DENIED.getCode());
            attributes.put(MSG, UserResultEnums.PERMISSION_DENIED.getMsg());
        } else if (ex instanceof IncorrectCredentialsException){
            attributes.put(CODE, UserResultEnums.USERNAME_OR_PASSWORD_ERROR.getCode());
            attributes.put(MSG, UserResultEnums.USERNAME_OR_PASSWORD_ERROR.getMsg());
        }else {
            logger.info(ex.toString());
            logger.info("MyExceptionHandler-----------------------------------------------");

        }
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }
}