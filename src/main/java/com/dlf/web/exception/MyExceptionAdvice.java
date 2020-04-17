package com.dlf.web.exception;

import com.dlf.web.dto.GlobalResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MyExceptionAdvice {

    /**
     * 全局异常捕捉处理
     * 错误不抛给调用方
     * @param e
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public GlobalResultDTO errorHandler(Exception e) {
        log.error("捕获到Exception异常", e);
        return GlobalResultDTO.FAIL();
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * 自定义异常抛出错误
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public GlobalResultDTO myErrorHandler(MyException e) {
        return GlobalResultDTO.FAIL(e.getErrorCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = UnauthenticatedException.class)
    public GlobalResultDTO unauthenticatedException(UnauthenticatedException e){
        return GlobalResultDTO.FAIL(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    public GlobalResultDTO unauthorizedException(UnauthorizedException e){
        return GlobalResultDTO.FAIL(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public GlobalResultDTO incorrectCredentialsException(IncorrectCredentialsException e){
        return GlobalResultDTO.FAIL(e.getMessage());
    }

}
