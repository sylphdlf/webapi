package com.dlf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;


/**
 * Created by Administrator on 2017/4/28.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class Application {


//    @Value("${upload.comm.maxsize}")
//    private String uploadMaxSize;
//    @Value("${request.comm.maxsize}")
//    private String requestMaxSize;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize(DataSize.of(200, DataUnit.MEGABYTES)); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.of(200, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}
