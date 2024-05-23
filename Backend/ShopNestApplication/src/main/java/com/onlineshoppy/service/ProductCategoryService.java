package com.onlineshoppy.service;



import com.onlineshoppy.exception.ProductAlreadyExists;
import com.onlineshoppy.exception.ProductCategoryNotFound;
import com.onlineshoppy.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    public List<ProductCategory> getAllProductCategory();
    public ProductCategory addProductCategory(ProductCategory productCategory) throws ProductAlreadyExists;
    ProductCategory getProductCategoryByName(String categoryName) throws ProductCategoryNotFound;

}
