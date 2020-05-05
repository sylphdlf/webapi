package com.dlf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.MsgReqDTO;
import com.dlf.web.dto.UserInfo;
import com.dlf.web.utils.Md5Utils;
import com.dlf.web.utils.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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