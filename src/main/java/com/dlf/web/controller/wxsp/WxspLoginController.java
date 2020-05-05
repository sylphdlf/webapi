package com.dlf.web.controller.wxsp;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.anno.KeyVerifyAnno;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.WxUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wxsp")
public class WxspLoginController {

    @Resource
    RestTemplate restTemplate;
    @Value("${router.url}")
    private String routerUrl;

//    @UrlPermissionIgnoreAnno
//    @KeyVerifyAnno
//    @RequestMapping(value = "/register",method = RequestMethod.POST)
//    public GlobalResultDTO register(@RequestBody WxUserDTO wxUserDTO, HttpServletRequest request){
//        return restTemplate.postForObject(routerUrl + request.getServletPath(), wxUserDTO, GlobalResultDTO.class);
//    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public GlobalResultDTO register(@RequestBody JSONObject jsonObject){
        return restTemplate.postForObject("http://ROUTER/service/user/wxspRegister", jsonObject, GlobalResultDTO.class);
    }

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public GlobalResultDTO checkUser(@RequestBody JSONObject jsonObject){
        return restTemplate.postForObject("http://ROUTER/service/user/checkWxspUser", jsonObject, GlobalResultDTO.class);
    }
}
