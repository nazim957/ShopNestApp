package com.onlineshoppy.repository;


import com.onlineshoppy.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    public Customer findByEmail(String email);
}
