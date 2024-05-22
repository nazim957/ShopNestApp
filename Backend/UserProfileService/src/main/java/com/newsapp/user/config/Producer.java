package com.newsapp.user.config;


import com.newsapp.user.controller.UserController;
import domain.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;
    private Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void sendMessageToRabbitMq(UserDTO userDTO)
    {
        logger.info("Producing User Credentials to Message Bus!");
        rabbitTemplate.convertAndSend(exchange.getName(),"user_routing",userDTO);
    }

    public void sendUpdateMessageToRabbitMq(UserDTO userDTO)
    {
        logger.info("Producing Updated User Credentials to Message Bus!");
        rabbitTemplate.convertAndSend(exchange.getName(),"update_routing",userDTO);
    }
}
