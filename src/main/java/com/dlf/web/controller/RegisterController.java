package com.dlf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.dto.GlobalResultDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class RegisterController {

    @Resource
    RestTemplate restTemplate;

    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public GlobalResultDTO registerWithCode(@RequestBody JSONObject jsonObject){
       return restTemplate.postForObject("http://ROUTER/service/register", jsonObject, GlobalResultDTO.class);
    }
    @RequestMapping(value = "/wxspRegister", method = RequestMethod.POST)
    public GlobalResultDTO wxspRegister(@RequestBody JSONObject jsonObject){
        return restTemplate.postForObject("http://ROUTER/service/user/wxspRegister", jsonObject, GlobalResultDTO.class);
    }
}
