package com.onlineshoppy.repository;


import com.onlineshoppy.model.Product;
import com.onlineshoppy.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {

    boolean existsByCategoryName(String categoryName);

    Optional<ProductCategory> findByCategoryNameIgnoreCase(String categoryName);
}
