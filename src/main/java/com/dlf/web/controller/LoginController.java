package com.dlf.web.controller;

import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.config.shiro.MobileVerifyCodeToken;
import com.dlf.web.dto.BaseReqDTO;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserVO;
import com.dlf.web.enums.GlobalResultEnum;
import com.dlf.web.enums.UserResultEnums;
import com.dlf.web.controller.restTemplate.RestTemplateForRouter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/login")
public class LoginController {
    @Autowired
    RestTemplateForRouter restTemplateForRouter;
    /**
     * 登录
     * @param reqDTO
     * @return
     */
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public GlobalResultDTO login(@RequestBody BaseReqDTO<UserVO> reqDTO) {
        Subject subject = SecurityUtils.getSubject();
        UserVO userVO = reqDTO.getData();
        //验证码登录
        if(StringUtils.isNotBlank(userVO.getPassword())){
            UsernamePasswordToken token = new UsernamePasswordToken(userVO.getUsername(), userVO.getPassword());
            try {
                subject.login(token);
            }catch (AuthenticationException e){
                return GlobalResultDTO.FAIL(GlobalResultEnum.FAIL.getCode(), e.getMessage());
            }
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("type", ((UserVO)SecurityUtils.getSubject().getPrincipal()).getType());
            returnMap.put("ut", subject.getSession().getId().toString());
            return GlobalResultDTO.SUCCESS(returnMap);
        }else {
            return GlobalResultDTO.FAIL(UserResultEnums.PASSWORD_OR_VERIFY_NULL.getCode(), UserResultEnums.PASSWORD_OR_VERIFY_NULL.getMsg());
        }
    }

    /**
     * 未登录
     * @return
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
    public GlobalResultDTO logout(@RequestBody BaseReqDTO reqDTO){
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
