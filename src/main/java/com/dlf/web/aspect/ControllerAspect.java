package com.dlf.web.aspect;

import com.dlf.web.dto.UserVO;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 在调用RestTemplateFacade.postForJsonObject方法时，自动加入userId和type
 */
@Aspect
@Component
public class ControllerAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "execution(* com.dlf.web.controller.restTemplate.*.*(..))")
    private void beforeRequest(){};
    /**
     * 对于插入语句自动添加userId, type(用户类型)
     * @param jp
     */
    @Before("beforeRequest()")
    public void setUser(JoinPoint jp){
        if (jp.getArgs() == null) {
            return;
        }
        for(Object bean : jp.getArgs()){
            if(bean instanceof String){
                continue;
            }
            try {
                UserVO userVO = (UserVO)SecurityUtils.getSubject().getPrincipal();
                PropertyUtils.setProperty(bean, "userId", userVO.getId());
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }
}