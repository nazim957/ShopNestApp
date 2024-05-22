package com.newsapp.user.repository;

import com.newsapp.user.model.ForgotPassword;
import com.newsapp.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
   User findByEmail(String email);


}
