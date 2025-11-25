package com.dzerain.product.service;

import com.dzerain.product.dto.ProductRequest;
import com.dzerain.product.dto.ProductResponse;
import com.dzerain.product.dto.ProductSummaryResponse;
import com.dzerain.product.exception.ResourceNotFoundException;
import com.dzerain.product.kafka.event.ProductCreatedEvent;
import com.dzerain.product.kafka.producer.ProductEventProducer;
import com.dzerain.product.mapper.ProductMapper;
import com.dzerain.product.model.Category;
import com.dzerain.product.model.Product;
import com.dzerain.product.repository.CategoryRepository;
import com.dzerain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

  private final ProductRepository repository;
  private final CategoryRepository categoryRepository;
  private final ProductEventProducer productEventProducer;

  public ProductService(
      ProductRepository repository,
      CategoryRepository categoryRepository,
      ProductEventProducer productEventProducer) {
    this.repository = repository;
    this.categoryRepository = categoryRepository;
    this.productEventProducer = productEventProducer;
  }

  public List<ProductResponse> findAll(String name) {
    List<Product> products =
        (name == null || name.isBlank())
            ? repository.findAll()
            : repository.findByNameContainingIgnoreCase(name);
    return products.stream().map(ProductMapper::toResponse).collect(Collectors.toList());
  }

  public ProductResponse findById(Long id) {
    Product product =
        repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto " + id + " no encontrado"));
    return ProductMapper.toResponse(product);
  }

  public ProductResponse create(ProductRequest request) {
    Category category =
        categoryRepository
            .findById(request.categoryId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Categoría " + request.categoryId() + " no encontrada"));

    Product product = new Product();
    Product saved = repository.save(ProductMapper.toEntity(request, product, category));

    ProductCreatedEvent event =
        new ProductCreatedEvent(
            saved.getId(),
            saved.getName(),
            saved.getDescription(),
            saved.getPrice(),
            saved.getCategory().getId());
    productEventProducer.publishProductCreated(event);

    return ProductMapper.toResponse(saved);
  }

  public ProductResponse update(Long id, ProductRequest request) {
    Product product =
        repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto " + id + " no encontrado"));
    Category category =
        categoryRepository
            .findById(request.categoryId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Categoría " + request.categoryId() + " no encontrada"));
    ProductMapper.toEntity(request, product, category);
    Product updated = repository.save(ProductMapper.toEntity(request, product, category));
    return ProductMapper.toResponse(ProductMapper.toEntity(request, updated, category));
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Producto " + id + " no encontrado");
    }
    repository.deleteById(id);
  }

  public List<ProductSummaryResponse> findAllSummary() {
    List<Product> products = repository.findAll();
    return products.stream().map(ProductMapper::toSummaryResponse).collect(Collectors.toList());
  }
}
