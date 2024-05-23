package com.onlineshoppy.service;



import com.onlineshoppy.exception.ProductCategoryNotFound;
import com.onlineshoppy.exception.ProductNotExists;
import com.onlineshoppy.model.Product;
import com.onlineshoppy.model.ProductCategory;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();

    public Product addProduct(Product product) throws ProductCategoryNotFound;

    List<Product> searchProductByNameContaining(String name) throws ProductNotExists;

    public Product getProductById(Long productId) throws ProductNotExists;

//    ################################################

    public List<Product> getProductsByCategory(ProductCategory productCategory);

}
