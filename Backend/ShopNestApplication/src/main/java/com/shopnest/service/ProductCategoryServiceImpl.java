package com.shopnest.service;

import com.shopnest.exception.ProductAlreadyExists;
import com.shopnest.exception.ProductCategoryNotFound;
import com.shopnest.model.ProductCategory;
import com.shopnest.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {


    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategory> getAllProductCategory() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory addProductCategory(ProductCategory productCategory) throws ProductAlreadyExists {
        String categoryName = productCategory.getCategoryName();

        if (productCategoryRepository.existsByCategoryName(categoryName)) {
            throw new ProductAlreadyExists("Product Category '" + categoryName + "' Already Exists!!");
        }
        return productCategoryRepository.saveAndFlush(productCategory);
    }

    @Override
    public ProductCategory getProductCategoryByName(String categoryName) throws ProductCategoryNotFound {
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findByCategoryNameIgnoreCase(categoryName);
        return optionalProductCategory.orElseThrow(() ->
                new ProductCategoryNotFound("Product Category not found with name: " + categoryName));
    }


}
