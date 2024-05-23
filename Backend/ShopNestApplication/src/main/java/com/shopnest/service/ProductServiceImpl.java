package com.shopnest.service;


import com.shopnest.exception.ProductCategoryNotFound;
import com.shopnest.exception.ProductNotExists;
import com.shopnest.model.Product;
import com.shopnest.model.ProductCategory;
import com.shopnest.repository.ProductCategoryRepository;
import com.shopnest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) throws ProductCategoryNotFound {
        boolean productCategoryExists = productCategoryRepository.existsById(product.getProductCategory().getCategoryId());
        if(productCategoryExists){
            return productRepository.saveAndFlush(product);
        }
        throw new ProductCategoryNotFound("Product Category Not Exists in the Database!!");
    }

    @Override
    public List<Product> searchProductByNameContaining(String name) throws ProductNotExists {
        Optional<List<Product>> optionalProducts = productRepository.findByNameContainingIgnoreCase(name);
        List<Product> products = optionalProducts.orElseThrow(() -> new ProductNotExists("No products found with the name containing '" + name + "'."));
        return products;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotExists {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElseThrow(() -> new ProductNotExists("Product not found with ID: " + productId));
    }


    //    ################################################
    @Override
    public List<Product> getProductsByCategory(ProductCategory productCategory) {
        List<Product> byProductCategory = productRepository.findByProductCategory(productCategory);
        if(!byProductCategory.isEmpty())
        {
            return byProductCategory;
        }
        return null;
    }


}
