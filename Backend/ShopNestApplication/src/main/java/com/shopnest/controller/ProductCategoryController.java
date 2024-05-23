package com.shopnest.controller;


import com.shopnest.exception.InvalidTokenException;
import com.shopnest.exception.ProductAlreadyExists;
import com.shopnest.exception.ProductCategoryNotFound;
import com.shopnest.model.ProductCategory;
import com.shopnest.security.JWTTokenValidator;
import com.shopnest.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class ProductCategoryController {


    private final ProductCategoryService productCategoryService;
    private final JWTTokenValidator jwtTokenValidator;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService, JWTTokenValidator jwtTokenValidator) {
        this.productCategoryService = productCategoryService;
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts()
    {
        List<ProductCategory> productCategoriesList = productCategoryService.getAllProductCategory();
        if(productCategoriesList!=null)
        {
            return new ResponseEntity<>(productCategoriesList, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Product List is Empty", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping
    public ResponseEntity<ProductCategory> addProductCategory(@Parameter(hidden = true) @RequestHeader("Authorization") String token,@RequestBody ProductCategory productCategory) throws ProductAlreadyExists, InvalidTokenException {
        validateToken(token);
        try {
            ProductCategory addedProductCategory = productCategoryService.addProductCategory(productCategory);
            return new ResponseEntity<ProductCategory>(addedProductCategory, HttpStatus.CREATED);
        } catch (ProductAlreadyExists e) {
            throw new ProductAlreadyExists(e.getCustomMessage());
        }
    }

//*******************TDDO**********************************
    @GetMapping("/{categoryName}")
    public ResponseEntity<?> getProductsByCategoryName(@PathVariable("categoryName") String categoryName) {
        try {
            ProductCategory productCategory = productCategoryService.getProductCategoryByName(categoryName);
            return new ResponseEntity<>(productCategory.getProductSet(), HttpStatus.OK);
        } catch (ProductCategoryNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private void validateToken(String token) throws InvalidTokenException {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new InvalidTokenException("Missing or Invalid Token");
        }

        // Validate the JWT token
        Map<String, String> validationMap = jwtTokenValidator.validateToken(token.substring(7)); // Remove "Bearer " prefix

        // Check if the token is valid
        if (validationMap.containsKey("error")) {
            throw new InvalidTokenException("Invalid Token");
        }
    }

}
