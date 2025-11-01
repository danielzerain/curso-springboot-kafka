package com.dzerain.product.controller;

import com.dzerain.product.dto.ProductRequest;
import com.dzerain.product.dto.ProductResponse;
import com.dzerain.product.dto.ProductSummaryResponse;
import com.dzerain.product.service.ProductService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> findAll(
      @RequestParam(required = false) String name) {
    return ResponseEntity.ok(service.findAll(name));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> update(
      @PathVariable Long id, @RequestBody ProductRequest request) {
    return ResponseEntity.ok(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/summary")
  public ResponseEntity<List<ProductSummaryResponse>> findAllSummary() {
    return ResponseEntity.ok(service.findAllSummary());
  }
}
