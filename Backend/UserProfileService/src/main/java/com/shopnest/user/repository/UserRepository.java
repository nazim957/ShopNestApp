package com.shopnest.user.repository;

import com.shopnest.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
   User findByEmail(String email);


}
