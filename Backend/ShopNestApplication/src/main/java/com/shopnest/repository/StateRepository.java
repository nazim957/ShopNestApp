package com.shopnest.repository;


import com.shopnest.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {

    List<State> findByCountryCodeIgnoreCase(String code);
}
