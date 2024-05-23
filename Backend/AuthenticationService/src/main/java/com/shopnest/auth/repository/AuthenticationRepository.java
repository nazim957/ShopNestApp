package com.shopnest.auth.repository;

import com.shopnest.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<User,String> {
    public User findByEmailAndPassword(String email, String password);

    public User findByEmail(String email);
}
