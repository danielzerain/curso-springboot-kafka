package com.dzerain.product.mapper;

import com.dzerain.product.dto.ProductRequest;
import com.dzerain.product.dto.ProductResponse;
import com.dzerain.product.dto.ProductSummaryResponse;
import com.dzerain.product.model.Product;

public final class ProductMapper {

  private ProductMapper() {}

  public static ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getStock(),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }

  public static Product toEntity(ProductRequest request, Product entity) {
    entity.setName(request.name());
    entity.setDescription(request.description());
    entity.setPrice(request.price());
    entity.setStock(request.stock());
    return entity;
  }

  public static ProductSummaryResponse toSummaryResponse(Product product) {
    return new ProductSummaryResponse(
        product.getId(), product.getName(), product.getDescription(), product.getPrice());
  }
}
