package com.dlf.web.schedule;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.filter.UrlRedirectFilter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalTime;

@Component
public class AccessCountSchedule {

    @Resource
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron="0/5 * * * * ?")
    public void accessCountPush(){
        LocalTime now = LocalTime.now();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time", now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
        jsonObject.put("count", UrlRedirectFilter.accessCount.getAndSet(0));
        rabbitTemplate.convertAndSend("topicExchange", "topic.access", jsonObject.toJSONString());
    }
}
