package com.dlf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/7.
 */
@RestController
public class CommController {

    @Resource
    RestTemplate restTemplate;

    @Value("${router.url}")
    private String routerUrl;

    /**
     * 访问service的统一入口
     * @return
     */
    @RequestMapping(value = "/comm",method = RequestMethod.POST)
    public GlobalResultDTO comm(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        Subject subject = SecurityUtils.getSubject();
        jsonObject.put("userId", ((UserInfo)subject.getPrincipal()).getId());
        return restTemplate.postForObject(routerUrl + request.getAttribute("url"), jsonObject, GlobalResultDTO.class);
    }
}
