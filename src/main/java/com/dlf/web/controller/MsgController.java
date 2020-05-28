package com.dlf.web.controller;

import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.MsgReqDTO;
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
@RequestMapping("/msg")
public class MsgController {

    @Resource
    RestTemplate restTemplate;

    @Value("${router.url}")
    private String routerUrl;

    /**
     * 发送验证码接口
     * @param request
     * @param reqDTO
     * @return
     */
    @RequestMapping(value = "/getVerifyCode",method = RequestMethod.POST)
    public GlobalResultDTO getVerifyCode(HttpServletRequest request, @RequestBody MsgReqDTO reqDTO){
        return restTemplate.postForObject(routerUrl + request.getServletPath(), reqDTO, GlobalResultDTO.class);
    }
}
