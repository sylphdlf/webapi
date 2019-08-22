package com.dlf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;


/**
 * Created by Administrator on 2017/4/28.
 */
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
public class WebApplication {


//    @Value("${upload.comm.maxsize}")
//    private String uploadMaxSize;
//    @Value("${request.comm.maxsize}")
//    private String requestMaxSize;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebApplication.class);
        app.run(args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize("2MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("2MB");
        return factory.createMultipartConfig();
    }
}
