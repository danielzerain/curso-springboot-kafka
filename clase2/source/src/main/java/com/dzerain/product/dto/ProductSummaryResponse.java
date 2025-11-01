package com.dzerain.product.dto;

import java.math.BigDecimal;

public record ProductSummaryResponse(Long id, String name, String description, BigDecimal price) {}
