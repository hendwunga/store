package com.hend.store.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.hend.store.dto.ApiResponse;
import com.hend.store.dto.ProductRequest;
import com.hend.store.models.Category;
import com.hend.store.models.Product;
import com.hend.store.services.CategoryRepository;
import com.hend.store.services.ProductsRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "CRUD operasi untuk produk")
public class ProductRestController {

    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;

    public ProductRestController(ProductsRepository productsRepository,
                                  CategoryRepository categoryRepository) {
        this.productsRepository = productsRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @Operation(summary = "List semua produk", description = "Mengembalikan daftar semua produk. Supports pencarian berdasarkan nama atau brand.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Berhasil mengambil data produk")
    })
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(
            @Parameter(description = "Kata kunci pencarian (opsional)") @RequestParam(required = false) String search) {

        List<Product> products;
        if (search != null && !search.isBlank()) {
            products = productsRepository
                .findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(search, search);
        } else {
            products = productsRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        return ResponseEntity.ok(ApiResponse.ok("Products retrieved successfully", products));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detail satu produk", description = "Mengembalikan detail produk berdasarkan ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produk ditemukan"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produk tidak ditemukan", content = @Content)
    })
    public ResponseEntity<ApiResponse<Product>> getProductById(
            @Parameter(description = "ID produk") @PathVariable int id) {

        return productsRepository.findById(id)
            .map(product -> ResponseEntity.ok(ApiResponse.ok("Product found", product)))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Product with id " + id + " not found")));
    }

    @PostMapping
    @Operation(summary = "Buat produk baru", description = "Membuat produk baru dengan data yang dikirimkan")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Produk berhasil dibuat"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasi gagal", content = @Content)
    })
    public ResponseEntity<ApiResponse<Product>> createProduct(
            @Valid @RequestBody ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Category with id " + request.getCategoryId() + " not found"));
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setImageFileName(request.getImageUrl());

        Product saved = productsRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok("Product created successfully", saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update produk", description = "Mengupdate data produk berdasarkan ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produk berhasil diupdate"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produk tidak ditemukan", content = @Content),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasi gagal", content = @Content)
    })
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @Parameter(description = "ID produk") @PathVariable int id,
            @Valid @RequestBody ProductRequest request) {

        Product existing = productsRepository.findById(id).orElse(null);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Product with id " + id + " not found"));
        }

        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Category with id " + request.getCategoryId() + " not found"));
        }

        existing.setName(request.getName());
        existing.setBrand(request.getBrand());
        existing.setCategory(category);
        existing.setPrice(request.getPrice());
        existing.setDescription(request.getDescription());
        if (request.getImageUrl() != null) {
            existing.setImageFileName(request.getImageUrl());
        }

        Product updated = productsRepository.save(existing);
        return ResponseEntity.ok(ApiResponse.ok("Product updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Hapus produk", description = "Menghapus produk berdasarkan ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produk berhasil dihapus"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produk tidak ditemukan", content = @Content)
    })
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @Parameter(description = "ID produk") @PathVariable int id) {

        Product product = productsRepository.findById(id).orElse(null);
        if (product == null) {
            ApiResponse<Void> errorResponse = ApiResponse.error("Product with id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        productsRepository.delete(product);
        ApiResponse<Void> successResponse = ApiResponse.ok("Product deleted successfully", null);
        return ResponseEntity.ok(successResponse);
    }
}
