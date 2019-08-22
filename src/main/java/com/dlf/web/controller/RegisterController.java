package com.dlf.web.controller;

import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.config.shiro.RegisterAndLoginToken;
import com.dlf.web.controller.restTemplate.RestTemplateForRouter;
import com.dlf.web.dto.BaseReqDTO;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.enums.GlobalResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/reg")
public class RegisterController {

    @Autowired
    RestTemplateForRouter restTemplateForRouter;
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public GlobalResultDTO registerWithCode(@RequestBody BaseReqDTO reqDTO){
        GlobalResultDTO resultDTO = restTemplateForRouter.postForJsonObject(reqDTO);
        if (resultDTO.isSuccess()) {
            Map<String,Object> returnMap = (Map<String,Object>)resultDTO.getData();
            RegisterAndLoginToken token = new RegisterAndLoginToken((String)returnMap.get("userId"), (Integer)returnMap.get("type"));
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
            }catch (AuthenticationException e){
                return GlobalResultDTO.FAIL(GlobalResultEnum.FAIL.getCode(), e.getMessage());
            }
            return GlobalResultDTO.SUCCESS(returnMap);
        }
       return resultDTO;
    }
}
