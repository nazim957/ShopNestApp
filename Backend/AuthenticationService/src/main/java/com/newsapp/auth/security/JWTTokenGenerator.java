package com.newsapp.auth.security;


import com.newsapp.auth.model.User;


import java.util.Map;

public interface JWTTokenGenerator {

    Map<String, String> generateToken(User user);
}
