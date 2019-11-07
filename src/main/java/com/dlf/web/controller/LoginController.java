package com.dlf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.enums.GlobalResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/7.
 */
@RestController
public class LoginController {

    /**
     * 登录
     * @return GlobalResultDTO
     */
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public GlobalResultDTO login(@RequestBody JSONObject jsonObject) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(jsonObject.getString("username"), jsonObject.getString("password"));
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            return GlobalResultDTO.FAIL(GlobalResultEnum.FAIL.getCode(), e.getMessage());
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("ut", subject.getSession().getId().toString());
        return GlobalResultDTO.SUCCESS(returnMap);
    }

    /**
     * 未登录
     * @return GlobalResultDTO
     */
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/unAuth",method = RequestMethod.GET)
    public GlobalResultDTO unAuth() {
        return GlobalResultDTO.FAIL(GlobalResultEnum.LOG_OUT.getCode(), GlobalResultEnum.LOG_OUT.getMsg());
    }

    /**
     * 登出
     * @return
     */
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public GlobalResultDTO logout(@RequestBody JSONObject jsonObject){
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return GlobalResultDTO.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
            return GlobalResultDTO.FAIL(e.getMessage());
        }
    }
}
