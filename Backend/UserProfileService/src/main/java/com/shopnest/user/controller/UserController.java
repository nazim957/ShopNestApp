package com.shopnest.user.controller;

import com.shopnest.user.exception.SecurityMismatchException;
import com.shopnest.user.exception.UserAlreadyExistException;
import com.shopnest.user.exception.UserNotFoundException;
import com.shopnest.user.exception.UserNotVerifiedException;
import com.shopnest.user.model.ForgotPassword;
import com.shopnest.user.model.User;
import com.shopnest.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RestController annotation is used to create Restful web services using Spring MVC
 */
@RestController
/**
 * RequestMapping annotation maps HTTP requests to handler methods
 */
@CrossOrigin("*")
@RequestMapping("/api/v1/")
@Tag(name="User-Profile")
public class UserController {

    private final UserService userService;

     private ResponseEntity<?> responseEntity;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${app.controller.exception.message1}")
    private String message1;

    @Value("${app.controller.exception.message2}")
    private String message2;

    @Value("${app.controller.exception.message3}")
    private String message3;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistException {

        try {
            responseEntity = new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
            logger.info("User Details has been saved successfully!");
        } catch (UserAlreadyExistException e) {
            logger.info("User Details Already Exists!");
            throw new UserAlreadyExistException(e.getCustomMessage());
        }
        return responseEntity;
    }

    @PutMapping("/forgot/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable("email") String email, @RequestBody ForgotPassword forgotPassword) throws SecurityMismatchException, UserNotFoundException, UserNotVerifiedException {
        try {
            if (userService.updatePassword(email, forgotPassword)) {
              //  responseEntity = new ResponseEntity<>("Password Updated Successfully!!", HttpStatus.OK);
                responseEntity = ResponseEntity.ok().body("{\"message\": \"Password Updated Successfully!!\"}");
            }
        } catch (UserNotFoundException userNotFoundException) {
            logger.info("User Details Not Found");
            throw new UserNotFoundException(userNotFoundException.getCustomMessage());
        } catch (SecurityMismatchException ex) {
            logger.info("Security Question ans Answer Mismatch");
            throw new SecurityMismatchException(ex.getCustomMessage());
        } catch (UserNotVerifiedException e) {
            logger.info("User Not Verified!!");
            throw new UserNotVerifiedException(e.getCustomMessage());
        }
        return responseEntity;
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return userService.confirmEmail(confirmationToken);
    }
}
