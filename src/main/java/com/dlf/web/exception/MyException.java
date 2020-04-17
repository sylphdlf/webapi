package com.dlf.web.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义异常
 * Date: 2017/8/18
 *
 * @author dailf
 */
public class MyException extends RuntimeException{

    //容纳所有异常
    private List<Throwable> exceptionList = new ArrayList<Throwable>();
    private List<String> userIds = new ArrayList<String>();

    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    //构造函数，传递一个异常列表
    public MyException(List<? extends Throwable> exception){
        exceptionList.addAll(exception);
    }
    public MyException(String code, String msg, List<String> ids){
        super(msg);
        userIds.addAll(ids);
        this.errorCode = code;
    }
    //读取所有的异常
    public List<Throwable> getException(){
        return exceptionList;
    }
    public List<String> getUserIds(){ return userIds; }
    public MyException(Throwable throwable){
        super(throwable);
    }
    public MyException(String errorMsg){
        super(errorMsg);
        this.errorCode = "-1";
    }
    public MyException(String errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
    }
}
