package com.dlf.web.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }
//
//    @Bean("urlConnection")
//    public RestTemplate urlConnectionRestTemplate(){
//        return new RestTemplate(new SimpleClientHttpRequestFactory());
//    }
//
//    @Bean("httpClient")
//    @LoadBalanced
//    public RestTemplate httpClientRestTemplate(){
//        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//    }

    @Bean("OKHttp3")
    @LoadBalanced
    public RestTemplate OKHttp3RestTemplate(){
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        FastJsonHttpMessageConverter4 converter4 = new FastJsonHttpMessageConverter4();
        List<MediaType> mediaTypes = new ArrayList<>();

        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.MULTIPART_FORM_DATA);
        converter4.setSupportedMediaTypes(mediaTypes);

        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(converter4);
        return restTemplate;
    }
}
