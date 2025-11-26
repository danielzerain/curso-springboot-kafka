package com.dzerain.inventoryservice.service;

import com.dzerain.inventoryservice.exception.InventoryAlreadyExistsException;
import com.dzerain.inventoryservice.exception.ResourceNotFoundException;
import com.dzerain.inventoryservice.kafka.event.InventoryUpdatedEvent;
import com.dzerain.inventoryservice.kafka.event.OrderCancelledEvent;
import com.dzerain.inventoryservice.kafka.event.OrderConfirmedEvent;
import com.dzerain.inventoryservice.kafka.event.OrderPlacedEvent;
import com.dzerain.inventoryservice.kafka.producer.InventoryEventProducer;
import com.dzerain.inventoryservice.controller.dto.InventoryItemRequest;
import com.dzerain.inventoryservice.controller.dto.InventoryItemResponse;
import com.dzerain.inventoryservice.model.entity.InventoryItem;
import java.util.List;
import java.util.stream.Collectors;

import com.dzerain.inventoryservice.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

  private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

  private final InventoryRepository inventoryRepository;
  private final InventoryEventProducer eventProducer;

  public InventoryService(
      InventoryRepository inventoryRepository, InventoryEventProducer eventProducer) {
    this.inventoryRepository = inventoryRepository;
    this.eventProducer = eventProducer;
  }

  @Transactional
  public InventoryItemResponse createInventoryItem(InventoryItemRequest request) {
    log.info("Creating inventory item for productId: {}", request.getProductId());
    if (inventoryRepository.existsByProductId(request.getProductId())) {
      throw new InventoryAlreadyExistsException(
          "Inventory item already exists for productId: " + request.getProductId());
    }

    InventoryItem item =
        new InventoryItem(
            request.getProductId(), request.getProductName(), request.getInitialStock());

    InventoryItem saved = inventoryRepository.save(item);
    log.info("Inventory item created: {}", saved);

    return toResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<InventoryItemResponse> getAllInventoryItems() {
    log.debug("Fetching all inventory items");
    return inventoryRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public InventoryItemResponse getInventoryItemById(Long id) {
    log.debug("Fetching inventory item by id: {}", id);
    InventoryItem item =
        inventoryRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
    return toResponse(item);
  }

  @Transactional(readOnly = true)
  public InventoryItemResponse getInventoryItemByProductId(Long productId) {
    log.debug("Fetching inventory item by productId: {}", productId);
    InventoryItem item =
        inventoryRepository
            .findByProductId(productId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Inventory item not found for productId: " + productId));
    return toResponse(item);
  }

  @Transactional
  public void deleteInventoryItem(Long id) {
    log.info("Deleting inventory item with id: {}", id);
    if (!inventoryRepository.existsById(id)) {
      throw new ResourceNotFoundException("Inventory item not found with id: " + id);
    }
    inventoryRepository.deleteById(id);
    log.info("Inventory item deleted: id={}", id);
  }

  @Transactional
  public void processOrderPlaced(OrderPlacedEvent event) {
    log.info(
        "Processing order: orderId={}, productId={}, quantity={}",
        event.getOrderId(),
        event.getProductId(),
        event.getQuantity());
    try {
      InventoryItem item =
          inventoryRepository
              .findByProductId(event.getProductId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Inventory item not found for productId: " + event.getProductId()));

      log.debug(
          "Current stock before reservation: availableStock={}, reservedStock={}",
          item.getAvailableStock(),
          item.getReservedStock());

      if (!item.hasAvailableStock(event.getQuantity())) {
        log.warn(
            "Insufficient stock for order: orderId={}, productId={}, requested={}, available={}",
            event.getOrderId(),
            event.getProductId(),
            event.getQuantity(),
            item.getAvailableStock());

        OrderCancelledEvent cancelledEvent =
            new OrderCancelledEvent(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity(),
                item.getAvailableStock(),
                "Insufficient stock");
        eventProducer.publishOrderCancelled(cancelledEvent);

        return;
      }
      item.reserveStock(event.getQuantity());
      inventoryRepository.save(item);

      log.info(
          "Stock reserved successfully: orderId={}, productId={}, newAvailableStock={}, newReservedStock={}",
          event.getOrderId(),
          event.getProductId(),
          item.getAvailableStock(),
          item.getReservedStock());

      OrderConfirmedEvent confirmedEvent =
          new OrderConfirmedEvent(
              event.getOrderId(),
              event.getProductId(),
              event.getQuantity(),
              item.getAvailableStock(),
              item.getReservedStock());

      eventProducer.publishOrderConfirmed(confirmedEvent);

      InventoryUpdatedEvent inventoryEvent =
          new InventoryUpdatedEvent(
              item.getProductId(),
              item.getAvailableStock(),
              item.getReservedStock(),
              item.getTotalStock());
      eventProducer.publishInventoryUpdated(inventoryEvent);

    } catch (Exception e) {
      log.error(
          "Error processing order: orderId={}, error={}", event.getOrderId(), e.getMessage(), e);

      OrderCancelledEvent cancelledEvent =
          new OrderCancelledEvent(
              event.getOrderId(),
              event.getProductId(),
              event.getQuantity(),
              0,
              "Error processing order: " + e.getMessage());
      eventProducer.publishOrderCancelled(cancelledEvent);
    }
  }

  private InventoryItemResponse toResponse(InventoryItem item) {
    return new InventoryItemResponse(
        item.getId(),
        item.getProductId(),
        item.getProductName(),
        item.getAvailableStock(),
        item.getReservedStock(),
        item.getTotalStock(),
        item.getCreatedAt(),
        item.getUpdatedAt());
  }
}
