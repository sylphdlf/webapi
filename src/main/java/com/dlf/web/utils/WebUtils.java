package com.dlf.web.utils;

import com.dlf.web.dto.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    private static final ThreadLocal<String> ipLocal = new ThreadLocal<>();

    public static void setIp(String ip){
        ipLocal.set(ip);
    }

    public static void setIp(HttpServletRequest request){
        ipLocal.set(getRealIP(request));
    }

    public static String getIp(){
        return ipLocal.get();
    }
    /**
     * 获取绑定了用户信息的请求头
     * @return
     */
    public static HttpHeaders getHeaders(){
        Subject subject = SecurityUtils.getSubject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("userId", ((UserInfo)subject.getPrincipal()).getId() + "");
        headers.set("username", ((UserInfo)subject.getPrincipal()).getUsername());
        return headers;
    }

    /**
     * 获取真实访问ip
     * @param request
     * @return
     */
    public static String getRealIP(HttpServletRequest request){
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
