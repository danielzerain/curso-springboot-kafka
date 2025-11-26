package com.dzerain.inventoryservice.controller;

import com.dzerain.inventoryservice.common.DocsText;
import com.dzerain.inventoryservice.controller.dto.InventoryItemRequest;
import com.dzerain.inventoryservice.controller.dto.InventoryItemResponse;
import com.dzerain.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "inventory", description = "Operaciones de gestion de inventarios")
public class InventoryController {

  private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

  private final InventoryService inventoryService;

  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @PostMapping
  @Operation(
      summary = DocsText.CREAR_ITEM_INVENTARIO.SUMMARY,
      description = DocsText.CREAR_ITEM_INVENTARIO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Inventario Registrado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InventoryItemResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<InventoryItemResponse> createInventoryItem(
      @Valid @RequestBody InventoryItemRequest request) {
    log.info("POST /api/inventory - Creating inventory item: {}", request);
    InventoryItemResponse response = inventoryService.createInventoryItem(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  @Operation(
      summary = DocsText.LISTAR_INVENTARIO.SUMMARY,
      description = DocsText.LISTAR_INVENTARIO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado Procesado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InventoryItemResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<List<InventoryItemResponse>> getAllInventoryItems() {
    log.debug("GET /api/inventory - Fetching all inventory items");
    List<InventoryItemResponse> items = inventoryService.getAllInventoryItems();
    return ResponseEntity.ok(items);
  }

  @GetMapping("/{id}")
  @Operation(
      summary = DocsText.OBTENER_ITEM_ID.SUMMARY,
      description = DocsText.OBTENER_ITEM_ID.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Registro Obtenido",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InventoryItemResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<InventoryItemResponse> getInventoryItemById(
      @Parameter(description = "ID del item a buscar", required = true, example = "123")
          @PathVariable
          Long id) {
    log.debug("GET /api/inventory/{} - Fetching inventory item", id);
    InventoryItemResponse response = inventoryService.getInventoryItemById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/product/{productId}")
  @Operation(
      summary = DocsText.OBTENER_PRODUCTO_ID.SUMMARY,
      description = DocsText.OBTENER_PRODUCTO_ID.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto obtenido",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InventoryItemResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<InventoryItemResponse> getInventoryItemByProductId(
      @Parameter(description = "ID del producto a buscar", required = true, example = "123")
          @PathVariable
          Long productId) {
    log.debug("GET /api/inventory/product/{} - Fetching inventory item", productId);
    InventoryItemResponse response = inventoryService.getInventoryItemByProductId(productId);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = DocsText.ELIMINAR_ITEM_INVENTARIO.SUMMARY,
      description = DocsText.ELIMINAR_ITEM_INVENTARIO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Item del inventario eliminado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InventoryItemResponse.class))
            }),
        @ApiResponse(responseCode = "404", description = "Item no encontrado", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInventoryItem(
      @Parameter(description = "ID del item a eliminar", required = true, example = "123")
          @PathVariable
          Long id) {
    log.info("DELETE /api/inventory/{} - Deleting inventory item", id);
    inventoryService.deleteInventoryItem(id);
    return ResponseEntity.noContent().build();
  }
}
