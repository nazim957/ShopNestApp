package com.shopnest.repository;


import com.shopnest.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    public Customer findByEmail(String email);
}
