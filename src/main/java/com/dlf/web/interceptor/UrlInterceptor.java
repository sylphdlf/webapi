package com.dlf.web.interceptor;

import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.dlf.business.manager.kafka.KafkaService;
//import com.dlf.com.dlf.model.dto.user.UserResDTO;

public class UrlInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    KafkaService kafkaService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //根据注解判断url是否放行
        HandlerMethod handlerMethod = (HandlerMethod)o;
        UrlPermissionIgnoreAnno anno = handlerMethod.getMethodAnnotation(UrlPermissionIgnoreAnno.class);
        if(null != anno){
            return true;
        }
//        获取链接，检查权限
        Subject subject = SecurityUtils.getSubject();
        try {
            //管理员有全部权限
            subject.checkRole("admin");
        }catch (Exception e){
            subject.checkPermission(httpServletRequest.getServletPath());
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("post");
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("after");
    }

    /**
     * 获取真实访问ip
     * @param request
     * @return
     */
    private String getRealIP(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
