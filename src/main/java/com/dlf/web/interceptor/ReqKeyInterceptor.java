package com.dlf.web.interceptor;

import com.dlf.web.anno.KeyVerifyAnno;
import com.dlf.web.enums.GlobalResultEnum;
import com.dlf.web.exception.MyException;
import com.dlf.web.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证防重提交密钥
 */
public class ReqKeyInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
//        System.out.println(request.getRequestURI());
//        if(o instanceof HandlerMethod){
//            HandlerMethod handlerMethod = (HandlerMethod)o;
//            KeyVerifyAnno anno = handlerMethod.getMethodAnnotation(KeyVerifyAnno.class);
//            if(null == anno){
//                return true;
//            }
//        }else {
//            return true;
//        }
//        String realIP = WebUtils.getRealIP(request);
//        String reqKey = request.getHeader("reqKey");
//        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
//        String orgReqKey = valueOperations.get(realIP);
//        if(StringUtils.isEmpty(reqKey)){
//            throw new MyException(GlobalResultEnum.REQUEST_KEY_NULL.getCode(), GlobalResultEnum.REQUEST_KEY_NULL.getMsg());
//        }
//        if(StringUtils.isEmpty(orgReqKey) || !StringUtils.equals(reqKey, orgReqKey)){
//            throw new MyException(GlobalResultEnum.REQUEST_KEY_INVALID.getCode(), GlobalResultEnum.REQUEST_KEY_INVALID.getMsg());
//        }
//        stringRedisTemplate.delete(realIP);
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("post");
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("after");
    }

}
