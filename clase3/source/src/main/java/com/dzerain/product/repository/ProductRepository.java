package com.dzerain.product.repository;

import com.dzerain.product.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @EntityGraph(attributePaths = "category")
  @Override
  List<Product> findAll();

  @EntityGraph(attributePaths = "category")
  List<Product> findByNameContainingIgnoreCase(String name);

  @EntityGraph(attributePaths = "category")
  List<Product> findByCategoryId(Long categoryId);
}
