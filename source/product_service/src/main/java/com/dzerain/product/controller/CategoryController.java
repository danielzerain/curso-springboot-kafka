package com.dzerain.product.controller;

import com.dzerain.product.common.DocsText;
import com.dzerain.product.controller.dto.CategoryRequest;
import com.dzerain.product.controller.dto.CategoryResponse;
import com.dzerain.product.controller.dto.ProductResponse;
import com.dzerain.product.service.CategoryService;
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
@RequestMapping("/api/categories")
@Tag(name = "category", description = "Operaciones de gestion de categorias")
public class CategoryController {

  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }

  @GetMapping
  @Operation(
          summary = DocsText.LISTAR_CATEGORIAS.SUMMARY,
          description = DocsText.LISTAR_CATEGORIAS.DESCRIPTION)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Listado de Categorias obtenido correctamente",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          schema = @Schema(implementation = CategoryResponse.class))
                          }),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Error interno del servidor",
                          content = @Content)
          })
  public ResponseEntity<List<CategoryResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @PostMapping
  @Operation(
          summary = DocsText.CREAR_ITEM_CATEGORIA.SUMMARY,
          description = DocsText.CREAR_ITEM_CATEGORIA.DESCRIPTION)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "201",
                          description = "Categoria Registrada",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          schema = @Schema(implementation = CategoryResponse.class))
                          }),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Error interno del servidor",
                          content = @Content)
          })
  public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
  }

  @GetMapping("/{id}/products")
  @Operation(
          summary = DocsText.LISTAR_PRODUCTOS.SUMMARY,
          description = DocsText.LISTAR_PRODUCTOS.DESCRIPTION)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Listado de productos por categoria obtenidos correctamente",
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
  public ResponseEntity<List<ProductResponse>> findProducts(@PathVariable Long id) {
    return ResponseEntity.ok(service.findProducts(id));
  }
}
