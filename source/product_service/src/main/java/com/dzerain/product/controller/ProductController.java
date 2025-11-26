package com.dzerain.product.controller;

import com.dzerain.product.common.DocsText;
import com.dzerain.product.controller.dto.ProductRequest;
import com.dzerain.product.controller.dto.ProductResponse;
import com.dzerain.product.controller.dto.ProductSummaryResponse;
import com.dzerain.product.service.ProductService;
import java.util.List;

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

@RestController
@RequestMapping("/api/products")
@Tag(name = "product", description = "Operaciones de gestion de productos")
public class ProductController {

  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @GetMapping
  @Operation(
      summary = DocsText.LISTAR_PRODUCTO.SUMMARY,
      description = DocsText.LISTAR_PRODUCTO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado de productos obtenido correctamente",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<List<ProductResponse>> findAll(
      @RequestParam(required = false) String name) {
    return ResponseEntity.ok(service.findAll(name));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = DocsText.OBTENER_PRODUCTO.SUMMARY,
      description = DocsText.OBTENER_PRODUCTO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto obtenido correctamente",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductResponse.class))
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
  public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PostMapping
  @Operation(
      summary = DocsText.CREAR_PRODUCTO.SUMMARY,
      description = DocsText.CREAR_PRODUCTO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto Registrado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
  }

  @PutMapping("/{id}")
  @Operation(
      summary = DocsText.MODIFICAR_PRODUCTO.SUMMARY,
      description = DocsText.MODIFICAR_PRODUCTO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto modificado correctamente",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Registro no encontrado",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<ProductResponse> update(
      @PathVariable Long id, @Valid @RequestBody ProductRequest request) {
    return ResponseEntity.ok(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = DocsText.BORRAR_PRODUCTO.SUMMARY,
      description = DocsText.BORRAR_PRODUCTO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Registro no encontrado",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/summary")
  @Operation(
      summary = DocsText.OBTENER_RESUMEN_PRODUCTO.SUMMARY,
      description = DocsText.OBTENER_RESUMEN_PRODUCTO.DESCRIPTION)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado de Resumen obtenido correctamente",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProductSummaryResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content)
      })
  public ResponseEntity<List<ProductSummaryResponse>> findAllSummary() {
    return ResponseEntity.ok(service.findAllSummary());
  }
}
