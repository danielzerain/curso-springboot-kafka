package com.dzerain.product.service;

import com.dzerain.product.dto.CategoryRequest;
import com.dzerain.product.dto.CategoryResponse;
import com.dzerain.product.dto.ProductResponse;
import com.dzerain.product.exception.CategoryAlreadyExistsException;
import com.dzerain.product.exception.ResourceNotFoundException;
import com.dzerain.product.mapper.ProductMapper;
import com.dzerain.product.model.Category;
import com.dzerain.product.repository.CategoryRepository;
import com.dzerain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  public CategoryService(
      CategoryRepository categoryRepository, ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  public List<CategoryResponse> findAll() {
    return categoryRepository.findAll().stream()
        .map(
            category ->
                new CategoryResponse(
                    category.getId(), category.getName(), category.getDescription()))
        .toList();
  }

  @Transactional
  public CategoryResponse create(CategoryRequest request) {
    if (categoryRepository.existsByNameIgnoreCase(request.name())) {
      throw new CategoryAlreadyExistsException(request.name());
    }
    Category category = new Category();
    category.setName(request.name());
    category.setDescription(request.description());
    Category saved = categoryRepository.save(category);
    return new CategoryResponse(saved.getId(), saved.getName(), saved.getDescription());
  }

  public List<ProductResponse> findProducts(Long id) {
    Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoría " + id + " no encontrada"));
    return productRepository.findByCategoryId(category.getId()).stream()
        .map(ProductMapper::toResponse)
        .toList();
  }
}
