package com.shopnest.controller;


import com.shopnest.exception.InvalidTokenException;
import com.shopnest.exception.ProductCategoryNotFound;
import com.shopnest.exception.ProductNotExists;
import com.shopnest.model.Product;
import com.shopnest.security.JWTTokenValidator;
import com.shopnest.service.ProductCategoryService;
import com.shopnest.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {


    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final JWTTokenValidator jwtTokenValidator;

    @Autowired
    public ProductController(ProductService productService, ProductCategoryService productCategoryService, JWTTokenValidator jwtTokenValidator) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts()
    {
            return new ResponseEntity<List<Product>>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @RequestBody Product product) throws ProductCategoryNotFound, InvalidTokenException {
        validateToken(token);
        try {
            Product addedProduct = productService.addProduct(product);
            return new ResponseEntity<Product>(addedProduct, HttpStatus.CREATED);
        } catch (ProductCategoryNotFound e) {
            throw new ProductCategoryNotFound(e.getCustomMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductByNameContaining(@RequestParam String name) throws ProductNotExists {
        try {
            List<Product> products = productService.searchProductByNameContaining(name);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch (ProductNotExists e) {
            throw new ProductNotExists(e.getCustomMessage());
        }

    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) throws ProductNotExists{
        try {
            Product product = productService.getProductById(productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductNotExists e) {
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

//    *****************TODO**********************


//    @GetMapping("/getByCategoryId/{categoryId}")
//    public ResponseEntity<?> getyId(@PathVariable("categoryId") Long categoryId)
//    {
//        ProductCategory productCategoryById = productCategoryService.getProductCategoryById(categoryId);
//        if(productCategoryById!=null)
//        {
//            return new ResponseEntity<>(productCategoryById, HttpStatus.OK);
//        }
//        return new ResponseEntity<String>("Product List is Empty", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @GetMapping("/get/{catId}")
//    public ResponseEntity<?> getProduct(@PathVariable("catId") Long catId)
//    {
//        ProductCategory productById = productCategoryService.getProductCategoryById(catId);
//        productById.getProductSet().c
//    }

//    @GetMapping("/getByCategoryId/{categoryId}")
//    public ResponseEntity<?> getByCategoryId(@PathVariable("categoryId") Long categoryId) {
//        ProductCategory productCategoryById = productCategoryService.getProductCategoryById(categoryId);
//        if (productCategoryById != null) {
//            List<Product> productsByCategory = productService.getProductsByCategory(productCategoryById);
//            if (!productsByCategory.isEmpty()) {
//                return new ResponseEntity<>(productsByCategory, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("No products found for the specified category", HttpStatus.NO_CONTENT);
//            }
//        }
//        return new ResponseEntity<>("Product Category not found", HttpStatus.NOT_FOUND);
//    }

}
