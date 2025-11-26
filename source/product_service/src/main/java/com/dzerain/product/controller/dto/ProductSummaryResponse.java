package com.dzerain.product.controller.dto;

import java.math.BigDecimal;

public record ProductSummaryResponse(Long id, String name, String description, BigDecimal price) {}
