package com.dlf.web.interceptor;

import com.dlf.web.anno.UtVerifyAnno;
import com.dlf.web.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        //根据注解判断url是否放行
        HandlerMethod handlerMethod = (HandlerMethod)o;
        UtVerifyAnno anno = handlerMethod.getMethodAnnotation(UtVerifyAnno.class);
        if(null == anno){
            return true;
        }
        String ip = WebUtils.getRealIP(request);
        String ut = request.getHeader("ut");
//        throw new Exception("ut error");
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("post");
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("after");
    }

}
