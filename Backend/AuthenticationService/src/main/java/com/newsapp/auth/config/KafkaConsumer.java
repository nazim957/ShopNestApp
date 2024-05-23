package com.newsapp.auth.config;

import com.newsapp.auth.exception.UserAlreadyExistException;
import com.newsapp.auth.model.User;
import com.newsapp.auth.service.AuthServiceImpl;
import domain.UserDTO;
import org.json.*;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service

//@Component
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private AuthServiceImpl authService;

    @KafkaListener(topics = { "user-topic"}, groupId = "user-group")
    public void consume(UserDTO userDto) throws UserAlreadyExistException, JSONException {

//
//    @RabbitListener(queues="user_queue1")
//    public void getUserDtoFromRabbitMq(UserDTO userDto) throws UserAlreadyExistsException
//    {
//        System.out.println(userDto);
//        logger.info("Consuming User Credentials from Message Bus");
//        JSONObject json = new JSONObject(userDto);
//      //  System.out.println(json.toString());
//        String email = json.getString("email");
//       // System.out.println(email);
//        String password = json.getString("password");
//       // System.out.println(password);

        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setUserName(userDto.getUserName());
        authService.saveCredentials(user); // it stores to mysql
    }
}
