package com.dzerain.product.controller.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    Long categoryId,
    String categoryName,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt) {}
