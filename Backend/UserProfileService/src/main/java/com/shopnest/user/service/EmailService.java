package com.shopnest.user.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
@Service("emailService")
public class EmailService {


    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {

        javaMailSender.send(email);
    }

    public MimeMessage createMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    @Async
    public void sendHtmlEmail(MimeMessage email) {
        javaMailSender.send(email);
    }

}
