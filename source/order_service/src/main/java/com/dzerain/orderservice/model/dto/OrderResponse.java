package com.dzerain.orderservice.model.dto;

import com.dzerain.orderservice.model.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
    Long id,
    Long productId,
    Integer quantity,
    String customerName,
    String customerEmail,
    BigDecimal totalAmount,
    OrderStatus status,
    LocalDateTime createdAt
) {}