package com.dlf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserInfo;
import com.dlf.web.utils.Md5Utils;
import com.dlf.web.utils.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CommController {

    @Resource
    RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${router.url}")
    private String routerUrl;

    /**
     * 访问service的统一入口
     * @return
     */
    @RequestMapping(value = "/comm",method = RequestMethod.POST)
    public GlobalResultDTO comm(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        Subject subject = SecurityUtils.getSubject();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.set("userId", ((UserInfo)subject.getPrincipal()).getId() + "");
        headers.set("username", ((UserInfo)subject.getPrincipal()).getUsername())
        HttpEntity entity = new HttpEntity<>(jsonObject,headers)
        ResponseEntity<GlobalResultDTO> responseEntity = restTemplate.exchange(routerUrl + request.getAttribute("url"), HttpMethod.POST, entity, new ParameterizedTypeReference<GlobalResultDTO>() {});
        return responseEntity.getBody();
//        return restTemplate.postForObject(routerUrl + request.getAttribute("url"), jsonObject, GlobalResultDTO.class);
    }

    /**
     * 获取防重key
     * @param request
     * @return
     */
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/getKey",method = RequestMethod.GET)
    public GlobalResultDTO getKey(HttpServletRequest request) throws NoSuchAlgorithmException {
        String realIp = WebUtils.getRealIP(request);
        String key = Md5Utils.md5Encoding(realIp + UUID.randomUUID());
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(realIp, key, 60*30, TimeUnit.SECONDS);
        return GlobalResultDTO.SUCCESS(key);
    }
}
