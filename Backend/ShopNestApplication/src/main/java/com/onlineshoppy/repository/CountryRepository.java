package com.onlineshoppy.repository;


import com.onlineshoppy.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
