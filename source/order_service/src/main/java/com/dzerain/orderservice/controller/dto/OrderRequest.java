package com.dzerain.orderservice.controller.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record OrderRequest(
    @NotNull(message = "{order.id.notblank}") @Positive(message = "{order.id.positive}")
        Long productId,
    @NotNull(message = "{order.cantidad.notblank}")
        @Positive(message = "{order.cantidad.positive}")
        @Max(value = 100, message = "{order.cantidad.max}")
        Integer quantity,
    @NotBlank(message = "{order.client.notblank}")
        @Size(min = 3, max = 100, message = "{order.client.length}")
        String customerName,
    @NotBlank(message = "{order.email.notblank}") @Email(message = "{order.email.valid}")
        String customerEmail,
    @NotNull(message = "{order.monto.notblank}") @Positive(message = "{order.monto.positive}")
        BigDecimal totalAmount) {}
