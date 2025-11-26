package com.dzerain.product.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @NotBlank(message = "{category.name.notblank}")
        @Size(max = 80, message = "{category.name.maxsize}")
        String name,
    @Size(max = 255, message = "{category.description.maxsize}") String description) {}
