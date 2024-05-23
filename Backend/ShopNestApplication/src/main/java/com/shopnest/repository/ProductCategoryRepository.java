package com.shopnest.repository;


import com.shopnest.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {

    boolean existsByCategoryName(String categoryName);

    Optional<ProductCategory> findByCategoryNameIgnoreCase(String categoryName);
}
