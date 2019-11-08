package com.dlf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
    /**
     * 通用接口
     * @return
     */
    @RequestMapping(value = "/comm",method = RequestMethod.POST)
    public GlobalResultDTO comm(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        Subject subject = SecurityUtils.getSubject();
        jsonObject.put("userId", ((UserInfo)subject.getPrincipal()).getId());
        return restTemplate.postForObject("http://ROUTER/service" + request.getAttribute("url"), jsonObject, GlobalResultDTO.class);
    }
}
