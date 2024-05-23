package com.newsapp.auth.repository;

import com.newsapp.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<User,String> {
    public User findByEmailAndPassword(String email, String password);

    public User findByEmail(String email);
}
