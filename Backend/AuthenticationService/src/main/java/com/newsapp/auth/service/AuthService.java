package com.newsapp.auth.service;

import com.newsapp.auth.exception.InvalidCredentialsException;
import com.newsapp.auth.exception.UserAlreadyExistException;
import com.newsapp.auth.exception.UserNotVerifiedException;
import com.newsapp.auth.model.User;

public interface AuthService {

    public User saveCredentials(User user) throws UserAlreadyExistException;

    User findByEmailAndPassword(String email, String password) throws InvalidCredentialsException, UserNotVerifiedException;

    boolean updateUser(User user);

}
