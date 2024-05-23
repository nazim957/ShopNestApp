package com.shopnest.service;



import com.shopnest.exception.ProductAlreadyExists;
import com.shopnest.exception.ProductCategoryNotFound;
import com.shopnest.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    public List<ProductCategory> getAllProductCategory();
    public ProductCategory addProductCategory(ProductCategory productCategory) throws ProductAlreadyExists;
    ProductCategory getProductCategoryByName(String categoryName) throws ProductCategoryNotFound;

}
