package com.dzerain.product.controller;

import com.dzerain.product.dto.CategoryRequest;
import com.dzerain.product.dto.CategoryResponse;
import com.dzerain.product.dto.ProductResponse;
import com.dzerain.product.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
  }

  @GetMapping("/{id}/products")
  public ResponseEntity<List<ProductResponse>> findProducts(@PathVariable Long id) {
    return ResponseEntity.ok(service.findProducts(id));
  }
}
