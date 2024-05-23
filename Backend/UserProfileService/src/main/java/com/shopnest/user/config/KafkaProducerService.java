//package com.newsapp.user.config;
//
//import domain.UserDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaProducerService {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
//
//    @Autowired
//    KafkaTemplate<String, UserDTO> kafkaTemplate;
//
//    public void send(String topicName, String key, UserDTO user) {
//        kafkaTemplate.send(topicName, key, user);
//        LOGGER.info("User details sent to Kafka topic: " + topicName + " with key: " + key);
//    }
//
//    @Service
//    public static class Producer {
//        private final Logger logger = LoggerFactory.getLogger(Producer.class);
//
//        @Autowired
//        KafkaProducerService kafkaProducerService;
//
//        public void produce(UserDTO user) {
//            logger.info("Producing User Credentials to Kafka!");
//            kafkaProducerService.send("user-topic", user.getEmail(), user);
//        }
//
//        public void update(UserDTO user) {
//            logger.info("Producing Updated User Credentials to Kafka!");
//            kafkaProducerService.send("user-update-topic", user.getEmail(), user);
//        }
//    }
//}
