package com.shopnest.auth.security;


import com.shopnest.auth.model.User;


import java.util.Map;

public interface JWTTokenGenerator {

    Map<String, String> generateToken(User user);
}
