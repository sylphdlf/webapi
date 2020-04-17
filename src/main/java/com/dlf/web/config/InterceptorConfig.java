package com.dlf.web.config;

import com.dlf.web.interceptor.UrlInterceptor;
import com.dlf.web.interceptor.UserTicketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    UrlInterceptor urlInterceptor(){
        return new UrlInterceptor();
    }
    @Bean
    UserTicketInterceptor userTicketInterceptor(){
        return new UserTicketInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(urlInterceptor());
        registry.addInterceptor(userTicketInterceptor());
    }
}
