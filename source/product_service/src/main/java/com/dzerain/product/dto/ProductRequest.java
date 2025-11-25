package com.dzerain.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank(message = "{product.name.notblank}") @Size(max = 120) String name,
    @NotBlank @Size(max = 255) String description,
    @NotNull @DecimalMin(value = "0.0", inclusive = false, message = "{product.price.min}")
        BigDecimal price,
    @NotNull @Positive(message = "{product.stock.min}") Integer stock,
    @NotNull Long categoryId) {}
