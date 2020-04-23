package com.dlf.web.controller.restTemplate;

import com.dlf.web.dto.GlobalResultDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class RestTemplateForRouter<T> {

    @Resource
    RestTemplate restTemplate;

    public GlobalResultDTO<T> postForJsonObject(T reqDTO, String url){
        ResponseEntity<GlobalResultDTO<T>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, getHeader(reqDTO),
                new ParameterizedTypeReference<GlobalResultDTO<T>>() {
        });
        return responseEntity.getBody();
    }

//    public GlobalResultDTO postForJsonObject(String url, BaseReqDTO reqDTO){
//        return restTemplate.postForObject(url, getHeader(reqDTO), GlobalResultDTO.class);
//    }

    public static HttpEntity getHeader(Object reqDTO){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        return new HttpEntity<>(reqDTO,headers);
    }
}
