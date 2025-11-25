package com.dzerain.orderservice.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record OrderRequest(
    @NotNull(message = "El ID del producto es requerido")
    @Positive(message = "El ID del producto debe ser positivo")
    Long productId,

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser positiva")
    @Max(value = 100, message = "La cantidad no puede exceder 100")
    Integer quantity,

    @NotBlank(message = "El nombre del cliente es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    String customerName,

    @NotBlank(message = "El email del cliente es requerido")
    @Email(message = "El email debe ser válido")
    String customerEmail,

    @NotNull(message = "El monto total es requerido")
    @Positive(message = "El monto total debe ser positivo")
    BigDecimal totalAmount

) {}