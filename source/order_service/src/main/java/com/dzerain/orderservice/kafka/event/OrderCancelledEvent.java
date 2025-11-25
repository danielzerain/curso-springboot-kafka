package com.dzerain.orderservice.kafka.event;

import java.time.Instant;

public class OrderCancelledEvent {

    private Long orderId;
    private Long productId;
    private Integer requestedQuantity;
    private Integer availableStock;
    private String reason;
    private Instant timestamp;

    public OrderCancelledEvent() {
    }

    public OrderCancelledEvent(Long orderId, Long productId, Integer requestedQuantity,
                               Integer availableStock, String reason) {
        this.orderId = orderId;
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableStock = availableStock;
        this.reason = reason;
        this.timestamp = Instant.now();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "OrderCancelledEvent{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", requestedQuantity=" + requestedQuantity +
                ", availableStock=" + availableStock +
                ", reason='" + reason + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}