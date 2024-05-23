package com.newsapp.auth.service;

import com.newsapp.auth.exception.InvalidCredentialsException;
import com.newsapp.auth.exception.UserAlreadyExistException;

import com.newsapp.auth.exception.UserNotVerifiedException;
import com.newsapp.auth.model.User;
import com.newsapp.auth.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationRepository authenticationRepository;

    @Value("${app.service.message1}")
    private String message1;

    @Value("${app.service.message2}")
    private String message2;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public AuthServiceImpl(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public User saveCredentials(User user) throws UserAlreadyExistException {

        if(authenticationRepository.findById(user.getEmail()).isPresent()){
            throw new UserAlreadyExistException("User Credentials Already Exists! Try again with new one or Log In!!");
        }

        return authenticationRepository.save(user);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws InvalidCredentialsException, UserNotVerifiedException {


        User loggedInUser = authenticationRepository.findByEmail(email);
        if (loggedInUser != null && passwordEncoder.matches(password, loggedInUser.getPassword()) && loggedInUser.isEnabled()) {
            return loggedInUser;
        }

      else if (loggedInUser != null && passwordEncoder.matches(password, loggedInUser.getPassword()) && !loggedInUser.isEnabled()) {
          throw new UserNotVerifiedException("User Not verified!! Please check your email and verify your account");
        }

           throw new InvalidCredentialsException("Invalid Credentials!! Please check the credentials and try again");
    }

    @Override
    public boolean updateUser(User user) {
        User byEmail = authenticationRepository.findByEmail(user.getEmail());
        if(byEmail!=null){
            byEmail.setPassword(user.getPassword());
            byEmail.setEnabled(user.isEnabled());
            authenticationRepository.saveAndFlush(byEmail);
            return true;
        }
        return false;
    }

}
