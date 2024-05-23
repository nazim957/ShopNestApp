package com.shopnest.controller;

import com.shopnest.model.Country;
import com.shopnest.model.State;
import com.shopnest.repository.CountryRepository;
import com.shopnest.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CountryAndStateController {


    private CountryRepository countryRepository;
    private StateRepository stateRepository;

    @Autowired
    public CountryAndStateController(CountryRepository countryRepository, StateRepository stateRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return ResponseEntity.ok(Optional.ofNullable(countries).orElse(Collections.emptyList()));
    }


    @GetMapping("/states/search/findByCountryCode/{code}")
    public ResponseEntity<List<State>> getStateByCountryCode(@PathVariable("code") String code) {
        List<State> states = stateRepository.findByCountryCodeIgnoreCase(code);
        return ResponseEntity.ok(Optional.ofNullable(states).orElse(Collections.emptyList()));
    }
}
