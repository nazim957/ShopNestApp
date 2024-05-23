package com.shopnest.service;



import com.shopnest.exception.ProductCategoryNotFound;
import com.shopnest.exception.ProductNotExists;
import com.shopnest.model.Product;
import com.shopnest.model.ProductCategory;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();

    public Product addProduct(Product product) throws ProductCategoryNotFound;

    List<Product> searchProductByNameContaining(String name) throws ProductNotExists;

    public Product getProductById(Long productId) throws ProductNotExists;

//    ################################################

    public List<Product> getProductsByCategory(ProductCategory productCategory);

}
