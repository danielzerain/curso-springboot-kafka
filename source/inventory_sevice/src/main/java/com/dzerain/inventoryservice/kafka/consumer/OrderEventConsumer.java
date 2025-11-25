package com.dzerain.inventoryservice.kafka.consumer;

import com.dzerain.inventoryservice.kafka.event.OrderPlacedEvent;
import com.dzerain.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

  private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

  private final InventoryService inventoryService;

  public OrderEventConsumer(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @KafkaListener(
      topics = "ecommerce.orders.placed",
      groupId = "inventory-service"
  )
  public void consumeOrderPlaced(OrderPlacedEvent event) {
    log.info("Received OrderPlacedEvent: {}", event);

    try {
      inventoryService.processOrderPlaced(event);
      log.info("Order processed successfully: orderId={}", event.getOrderId());
    } catch (Exception e) {
      log.error("Error processing OrderPlacedEvent: orderId={}, error={}",
          event.getOrderId(), e.getMessage(), e);
    }
  }
}
