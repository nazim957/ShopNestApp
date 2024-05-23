package com.shopnest.repository;


import com.shopnest.model.Product;
import com.shopnest.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByProductCategory(ProductCategory productCategory);

    Optional<List<Product>> findByNameContainingIgnoreCase(String name);
}
