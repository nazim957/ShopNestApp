package com.shopnest.user.service;

import com.shopnest.user.config.Producer;
import com.shopnest.user.exception.SecurityMismatchException;
import com.shopnest.user.exception.UserAlreadyExistException;
import com.shopnest.user.exception.UserNotFoundException;
import com.shopnest.user.exception.UserNotVerifiedException;
import com.shopnest.user.model.ConfirmationToken;
import com.shopnest.user.model.ForgotPassword;
import com.shopnest.user.model.User;
import com.shopnest.user.repository.ConfirmationTokenRepository;
import com.shopnest.user.repository.UserRepository;
import domain.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    Producer producer;

//    @Autowired
//    KafkaProducerService.Producer producer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.service.message1}")
    private String message1;

    @Value("${app.service.message2}")
    private String message2;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, Producer producer, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.producer = producer;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailService emailService;



    @Override
    public User registerUser(User user) throws UserAlreadyExistException {

        UserDTO userdto=new UserDTO();
        userdto.setUserName(user.getUserName());
        userdto.setEmail(user.getEmail());
        userdto.setPassword(passwordEncoder.encode(user.getPassword()));
        userdto.setRole(user.getRole().toString());
        userdto.setEnabled(user.isEnabled());
        User byEmail = userRepository.findByEmail(user.getEmail());


        if (byEmail!=null) {
            throw new UserAlreadyExistException("Email Id " + user.getEmail() +". Already Exists on server!! Please Try again with new one or Log In!!");
        }else if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserAlreadyExistException("Email Id cannot be empty!!");
        } else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserAlreadyExistException("Password cannot be empty!!");
        }
//        else if (user.getRole() == null) {
//            throw new UserAlreadyExistException("Role cannot be empty");
//        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);   //storing in mongo database
        // producer.produce(userdto); //for kafka
        producer.sendMessageToRabbitMq(userdto);  // for RabbitMQ


        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("shopnestverify@outlook.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("Hi " + user.getUserName() + ",\n\n"
                + "Welcome to ShopNest! To complete your registration, please click the link below:\n"
                + "http://localhost:8081/api/v1/confirm-account?token=" + confirmationToken.getConfirmationToken() + "\n\n"
                + "Thank you for choosing ShopNest!\n"
                + "Best regards,\n"
                + "The ShopNest Team");

        emailService.sendEmail(mailMessage);

            return savedUser;
    }

    @Override
    public boolean updatePassword(String email, ForgotPassword forgotPassword) throws SecurityMismatchException, UserNotFoundException, UserNotVerifiedException {
        User byEmail = userRepository.findByEmail(email);

        if (byEmail != null) {
            UserDTO userdto = new UserDTO();
            userdto.setUserName(byEmail.getUserName());
            userdto.setEmail(byEmail.getEmail());
            userdto.setPassword(passwordEncoder.encode(forgotPassword.getNewPassword()));
            userdto.setRole(byEmail.getRole().toString());
            userdto.setEnabled(byEmail.isEnabled());
            if(!byEmail.isEnabled()){
                throw new UserNotVerifiedException("User Not verified!! Please check your email and verify your account");
            }
            if (byEmail.isEnabled() && byEmail.getSecurityQuestion().equals(forgotPassword.getSecurityQuestion()) && byEmail.getSecurityAnswer().equals(forgotPassword.getSecurityQuestionAnswer())) {

                byEmail.setPassword(passwordEncoder.encode(forgotPassword.getNewPassword()));
             //   byEmail.setPassword(forgotPassword.getNewPassword());
                userRepository.save(byEmail);
              //   producer.update(userdto);  for kafka
                   producer.sendUpdateMessageToRabbitMq(userdto);   // for RabbitMq
                return true;
            } else {
                throw new SecurityMismatchException("Security Question and Answer do not match! Please check and try again!!");
            }
        } else {
            throw new UserNotFoundException("User with email " + email + " Not Found on the server!!");
        }
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            UserDTO userdto=new UserDTO();
            userdto.setUserName(user.getUserName());
            userdto.setEmail(user.getEmail());
            userdto.setPassword(user.getPassword());
            userdto.setRole(user.getRole().toString());
            userdto.setEnabled(true);
            userRepository.save(user);
          //  producer.update(userdto);  //for kafka
            producer.sendUpdateMessageToRabbitMq(userdto);  //for RabbitMq

            // Return HTML with JavaScript for message display and redirection
            String htmlResponse = "<html><body>"
                    + "<p id='message'>Verifying email...</p>"
                    + "<script>"
                    + "setTimeout(function() {"
                    + "document.getElementById('message').innerText = 'Email verified successfully!';"
                    + "}, 2000);"  // Show 'Email verified successfully!' after 2 seconds
                    + "setTimeout(function() {"
                    + "document.getElementById('message').innerText = 'Redirecting to login...';"
                    + "}, 4000);"  // Show 'Redirecting to login...' after 4 seconds
                    + "setTimeout(function() {"
                    + "window.location.href = 'http://localhost:4200/login';"
                    + "}, 6000);"  // Redirect after 6 seconds
                    + "</script>"
                    + "</body></html>";

            return ResponseEntity.ok(htmlResponse);
        }
        String errorResponse = "<html><body><p>Error: Couldn't verify email</p></body></html>";
        return ResponseEntity.badRequest().body(errorResponse);
    }


}
