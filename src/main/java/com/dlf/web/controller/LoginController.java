package com.dlf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserInfo;
import com.dlf.web.enums.GlobalResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        subject.login(token);
        UserInfo principal = (UserInfo) subject.getPrincipal();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("lastIp", principal.getLastIp());
        resultMap.put("sessionId", subject.getSession().getId().toString());
        resultMap.put("lastLoginTime", principal.getUpdateTime());
        return GlobalResultDTO.SUCCESS(resultMap);
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
     * 出错
     * @return GlobalResultDTO
     */
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public GlobalResultDTO error() {
        return GlobalResultDTO.FAIL(GlobalResultEnum.FAIL.getCode(), GlobalResultEnum.FAIL.getMsg());
    }

    /**
     * 登出
     * @return
     */
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public GlobalResultDTO logout(){
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
