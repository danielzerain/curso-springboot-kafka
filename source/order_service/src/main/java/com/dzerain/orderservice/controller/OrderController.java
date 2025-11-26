package com.dzerain.orderservice.controller;

import com.dzerain.orderservice.common.DocsText;
import com.dzerain.orderservice.model.dto.OrderRequest;
import com.dzerain.orderservice.model.dto.OrderResponse;
import com.dzerain.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "orders", description = "Operaciones de generacion de orden de productos")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @Operation(
      summary = DocsText.CREAR_ORDEN.SUMMARY,
      description = DocsText.CREAR_ORDEN.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Orden Registrada",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = OrderResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
    OrderResponse response = orderService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  @Operation(
      summary = DocsText.LISTAR_ORDENES.SUMMARY,
      description = DocsText.LISTAR_ORDENES.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ordenes listadas correctamente",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = OrderResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<List<OrderResponse>> findAll() {
    List<OrderResponse> orders = orderService.findAll();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{id}")
  @Operation(
      summary = DocsText.OBTENER_ORDEN_ID.SUMMARY,
      description = DocsText.OBTENER_ORDEN_ID.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Orden obtenida por ID",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = OrderResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
    OrderResponse order = orderService.findById(id);
    return ResponseEntity.ok(order);
  }
}
