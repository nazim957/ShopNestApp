package com.shopnest.auth.service;

import com.shopnest.auth.exception.InvalidCredentialsException;
import com.shopnest.auth.exception.UserAlreadyExistException;
import com.shopnest.auth.exception.UserNotVerifiedException;
import com.shopnest.auth.model.User;

public interface AuthService {

    public User saveCredentials(User user) throws UserAlreadyExistException;

    User findByEmailAndPassword(String email, String password) throws InvalidCredentialsException, UserNotVerifiedException;

    boolean updateUser(User user);

}
