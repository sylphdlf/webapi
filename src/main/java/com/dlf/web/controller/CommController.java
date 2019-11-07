package com.dlf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.dto.GlobalResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/5/7.
 */
@RestController
public class CommController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;

    /**
     * 通用接口
     * @return
     */
    @RequestMapping(value = "/comm",method = RequestMethod.POST)
    public GlobalResultDTO entrance(HttpRequest httpRequest, JSONObject jsonObject){
        System.out.println(jsonObject);
        return GlobalResultDTO.SUCCESS();
    }
}
