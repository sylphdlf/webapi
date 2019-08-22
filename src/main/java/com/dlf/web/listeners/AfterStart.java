package com.dlf.web.listeners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by Administrator on 2017/5/7.
 */
@Component
public class AfterStart implements CommandLineRunner{
    //引入初始化sql文件
//    @Resource
//    private InitService initService;

    /**
     * 初始化操作
     * @param strings
     * @throws IOException
     */
    public void run(String... strings) throws IOException {
        System.out.println("com.dlf.web.listeners.AfterStart----------------------");
    }
}
