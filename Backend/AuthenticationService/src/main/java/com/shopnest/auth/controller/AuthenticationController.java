package com.shopnest.auth.controller;

import com.shopnest.auth.exception.InvalidCredentialsException;
import com.shopnest.auth.exception.UserAlreadyExistException;
import com.shopnest.auth.exception.UserNotVerifiedException;
import com.shopnest.auth.model.UserCredentials;
import com.shopnest.auth.security.JWTTokenGenerator;
import com.shopnest.auth.model.User;
import com.shopnest.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/api/v2/")
@RestController
public class AuthenticationController {


    private AuthService authService;

    private JWTTokenGenerator jwtTokenGenerator;

    ResponseEntity<?> responseEntity;
    @Value("${app.controller.exception.message1}")
    private String message1;

    @Value("${app.controller.exception.message2}")
    private String message2;

    @Value("${app.controller.exception.message3}")
    private String message3;

    @Autowired
    public AuthenticationController(AuthService authService, JWTTokenGenerator jwtTokenGenerator) {
        this.authService = authService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @GetMapping("Admin")
    public String getAdminData() {

        return "Welcome Admin You Are Good to Go";
    }

    @GetMapping("User")
    public String getUserData() {

        return "Welcome User You Are Good to Go";
    }

    @PostMapping("saveCredentials")
    public ResponseEntity<User> saveCredentials(@RequestBody User user) throws UserAlreadyExistException {
        try {
            responseEntity = new ResponseEntity<>(authService.saveCredentials(user), HttpStatus.CREATED);
        }
     catch (UserAlreadyExistException ex){
            throw new UserAlreadyExistException(ex.getCustomMessage());
     }
        logger.info("User Credentials has been consumed and saved successfully");
        return (ResponseEntity<User>) responseEntity;
    }

    @PutMapping("updateCredentials")
    public ResponseEntity<String> updateCredentials(@RequestBody User user) {
        try {
            if (authService.updateUser(user)) {
                logger.info("User Credentials has been updated and saved successfully");
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            } else {
                logger.info("User not found or password not updated");
                return new ResponseEntity<>("User not found or password not updated", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            logger.error("An error occurred while updating user credentials", ex);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody UserCredentials user) throws InvalidCredentialsException, UserNotVerifiedException {

        try{
            User retrievedUser = authService.findByEmailAndPassword(user.getEmail(),user.getPassword());
            /*
             * Create ResponseEntity with token generated by calling generateToken method of JwtTokenGenerator
             */
            Map<String, String> stringStringMap = jwtTokenGenerator.generateToken(retrievedUser);
            logger.info("TOKEN has been generated successfully");
            responseEntity = new ResponseEntity<>(stringStringMap,HttpStatus.OK);
        }

       catch (InvalidCredentialsException ex)
        {
            logger.error("Please Check the Credentials and try again");
            throw new InvalidCredentialsException(ex.getCustomMessage());
        } catch (UserNotVerifiedException ex) {
            logger.error("Please Check your Email and verify your account!!");
            throw new UserNotVerifiedException(ex.getCustomMessage());
        }
        return responseEntity;
    }

//    public User getCurrentUser(Principal principal){
//       return  this.authService.findByEmailAndPassword(principal.getName());
//    }

}
