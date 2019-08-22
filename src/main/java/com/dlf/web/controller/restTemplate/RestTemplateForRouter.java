package com.dlf.web.controller.restTemplate;

import com.dlf.web.dto.BaseReqDTO;
import com.dlf.web.dto.GlobalResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateForRouter<T> {

    public static final String ROUTER_SERVICE = "http://mat-router/mat/";

    @Autowired
    RestTemplate restTemplate;

    public GlobalResultDTO<T> postForJsonObject(BaseReqDTO reqDTO){
        ResponseEntity<GlobalResultDTO<T>> responseEntity = restTemplate.exchange(ROUTER_SERVICE, HttpMethod.POST, getHeader(reqDTO),
                new ParameterizedTypeReference<GlobalResultDTO<T>>() {
        });
        return responseEntity.getBody();
    }

    public GlobalResultDTO postForJsonObject(String url, BaseReqDTO reqDTO){
        return restTemplate.postForObject(url, getHeader(reqDTO), GlobalResultDTO.class);
    }

    public static HttpEntity getHeader(BaseReqDTO reqDTO){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        return new HttpEntity<>(reqDTO,headers);
    }
}
