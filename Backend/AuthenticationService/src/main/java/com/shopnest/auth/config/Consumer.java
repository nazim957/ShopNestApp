package com.shopnest.auth.config;

import com.shopnest.auth.exception.UserAlreadyExistException;
import com.shopnest.auth.model.User;
import com.shopnest.auth.service.AuthServiceImpl;
import domain.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private AuthServiceImpl authService;
    private Logger logger = LoggerFactory.getLogger(Consumer.class);
    @RabbitListener(queues="user_queue_shopnest")  // not required to invoke why because it is event handler
    public void getUserDtoFromRabbitMq(UserDTO userDto) throws UserAlreadyExistException
    {
        logger.info("Consuming User Credentials from Message Bus");
        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setUserName(userDto.getUserName());
        authService.saveCredentials(user); // it stores to mysql
    }

    @RabbitListener(queues="update_queue_shopnest")  // not required to invoke why because it is event handler
    public void getUpdatedUserDtoFromRabbitMq(UserDTO userDto) throws UserAlreadyExistException
    {
        logger.info("Consuming Updated User Credentials from Message Bus");
        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
     //   user.setRole(userDto.getRole());
      //  user.setUserName(userDto.getUserName());
        user.setEnabled(userDto.isEnabled());
        authService.updateUser(user); // it updates to postgres
    }
}
