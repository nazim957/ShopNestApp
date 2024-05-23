package com.shopnest.user.service;

import com.shopnest.user.exception.SecurityMismatchException;
import com.shopnest.user.exception.UserAlreadyExistException;
import com.shopnest.user.exception.UserNotFoundException;
import com.shopnest.user.exception.UserNotVerifiedException;
import com.shopnest.user.model.ForgotPassword;
import com.shopnest.user.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User registerUser(User user) throws UserAlreadyExistException;

//    User findByIdAndPassword(String id, String password) throws UserNotFoundException;
    boolean updatePassword(String email, ForgotPassword forgotPassword) throws SecurityMismatchException, UserNotFoundException, UserNotVerifiedException;

    ResponseEntity<?> confirmEmail(String confirmationToken);
}
