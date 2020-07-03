package com.dlf.web.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static String TOPIC_ACCESS = "topic.access";

    public Queue queue(){
        return new Queue(RabbitConfig.TOPIC_ACCESS);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(queue()).to(exchange()).with(TOPIC_ACCESS);
    }
}
