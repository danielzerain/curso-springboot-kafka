package com.dzerain.orderservice.service;

import com.dzerain.orderservice.kafka.consumer.OrderEventConsumer;
import com.dzerain.orderservice.kafka.event.OrderPlacedEvent;
import com.dzerain.orderservice.kafka.producer.OrderEventProducer;
import com.dzerain.orderservice.model.dto.OrderRequest;
import com.dzerain.orderservice.model.dto.OrderResponse;
import com.dzerain.orderservice.model.entity.Order;
import com.dzerain.orderservice.model.entity.OrderStatus;
import com.dzerain.orderservice.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

  private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);
  private final OrderRepository orderRepository;
  private final OrderEventProducer orderEventProducer;

  public OrderService(
      OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
    this.orderRepository = orderRepository;
    this.orderEventProducer = orderEventProducer;
  }

  @Transactional
  public OrderResponse create(OrderRequest request) {
    Order order = new Order();
    order.setProductId(request.productId());
    order.setQuantity(request.quantity());
    order.setCustomerName(request.customerName());
    order.setCustomerEmail(request.customerEmail());
    order.setTotalAmount(request.totalAmount());

    Order saved = orderRepository.save(order);

    OrderPlacedEvent event =
        new OrderPlacedEvent(
            saved.getId(),
            saved.getProductId(),
            saved.getQuantity(),
            saved.getCustomerName(),
            saved.getCustomerEmail(),
            saved.getTotalAmount());
    orderEventProducer.publishOrderPlaced(event);

    return mapToResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<OrderResponse> findAll() {
    return orderRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public OrderResponse findById(Long id) {
    Order order =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    return mapToResponse(order);
  }

  @Transactional
  public void confirmOrder(Long orderId) {
    log.info("Confirming order: orderId={}", orderId);

    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

    if (order.getStatus() != OrderStatus.PENDING) {
      log.warn(
          "Order is not PENDING, cannot confirm: orderId={}, currentStatus={}",
          orderId,
          order.getStatus());
      return;
    }
    order.setStatus(OrderStatus.CONFIRMED);
    orderRepository.save(order);

    log.info("Order confirmed: orderId={}, newStatus={}", orderId, order.getStatus());
  }

  @Transactional
  public void cancelOrder(Long orderId, String reason) {
    log.info("Cancelling order: orderId={}, reason={}", orderId, reason);

    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

    if (order.getStatus() != OrderStatus.PENDING) {
      log.warn(
          "Order is not PENDING, cannot cancel: orderId={}, currentStatus={}",
          orderId,
          order.getStatus());
      return;
    }

    order.setStatus(OrderStatus.CANCELLED);
    order.setCancellationReason(reason);
    orderRepository.save(order);

    log.info(
        "Order cancelled: orderId={}, newStatus={}, reason={}", orderId, order.getStatus(), reason);
  }

  private OrderResponse mapToResponse(Order order) {
      return new OrderResponse(
          order.getId(),
          order.getProductId(),
          order.getQuantity(),
          order.getCustomerName(),
          order.getCustomerEmail(),
          order.getTotalAmount(),
          order.getStatus(),
          order.getCreatedAt());
  }
}
