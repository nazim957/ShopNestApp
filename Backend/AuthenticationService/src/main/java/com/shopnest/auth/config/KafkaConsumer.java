//package com.newsapp.auth.config;
//
//import com.newsapp.auth.exception.UserAlreadyExistException;
//import com.newsapp.auth.model.User;
//import com.newsapp.auth.service.AuthServiceImpl;
//import domain.UserDTO;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class KafkaConsumer {
//    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
//    @Autowired
//    private AuthServiceImpl authService;
//
//    @KafkaListener(topics = { "user-topic"}, groupId = "user-group")
//    public void consume(UserDTO userDto) throws UserAlreadyExistException {
//        logger.info("Consuming User Credentials from Kafka");
//        User user=new User();
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setRole(userDto.getRole());
//        user.setUserName(userDto.getUserName());
//        authService.saveCredentials(user); // it stores to mysql
//    }
//
//    @KafkaListener(topics = {"user-update-topic"}, groupId = "user-group")
//    public void consumeUpdate(UserDTO userDto) throws UserAlreadyExistException {
//
//        logger.info("Consuming Updated User Credentials from Kafka");
//        User user=new User();
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//     //   user.setRole(userDto.getRole());
//      //  user.setUserName(userDto.getUserName());
//        user.setEnabled(userDto.isEnabled());
//        authService.updateUser(user); // it updates to postgres
//    }
//}
