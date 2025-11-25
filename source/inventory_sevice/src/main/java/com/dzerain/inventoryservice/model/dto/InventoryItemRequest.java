package com.dzerain.inventoryservice.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InventoryItemRequest {

  @NotNull(message = "{inventory.id.notblank}")
  private Long productId;

  @NotBlank(message = "{inventory.productoName.notblank}")
  private String productName;

  @NotNull(message = "{inventory.productoName.stock.notblank}")
  @Min(value = 0, message = "{inventory.productoName.stock.min}")
  private Integer initialStock;

  public InventoryItemRequest() {}

  public InventoryItemRequest(Long productId, String productName, Integer initialStock) {
    this.productId = productId;
    this.productName = productName;
    this.initialStock = initialStock;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Integer getInitialStock() {
    return initialStock;
  }

  public void setInitialStock(Integer initialStock) {
    this.initialStock = initialStock;
  }

  @Override
  public String toString() {
    return "InventoryItemRequest{"
        + "productId="
        + productId
        + ", productName='"
        + productName
        + '\''
        + ", initialStock="
        + initialStock
        + '}';
  }
}
