package com.hend.store.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hend.store.dto.ApiResponse;
import com.hend.store.dto.CategoryRequest;
import com.hend.store.models.Category;
import com.hend.store.services.CategoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "CRUD operasi untuk kategori")
public class CategoryRestController {

    private final CategoryRepository categoryRepository;

    public CategoryRestController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @Operation(summary = "List semua kategori", description = "Mengembalikan daftar semua kategori yang tersedia")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(ApiResponse.ok("Categories retrieved successfully", categories));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detail satu kategori", description = "Mengembalikan detail kategori berdasarkan ID")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(
            @Parameter(description = "ID kategori") @PathVariable int id) {

        return categoryRepository.findById(id)
            .map(category -> ResponseEntity.ok(ApiResponse.ok("Category found", category)))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Category with id " + id + " not found")));
    }

    @PostMapping
    @Operation(summary = "Buat kategori baru", description = "Membuat kategori baru (hanya admin)")
    public ResponseEntity<ApiResponse<Category>> createCategory(
            @Valid @RequestBody CategoryRequest request) {

        Category category = new Category(request.getName());
        try {
            Category saved = categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Category created successfully", saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Category name may already exist"));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update kategori", description = "Mengupdate nama kategori berdasarkan ID")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @Parameter(description = "ID kategori") @PathVariable int id,
            @Valid @RequestBody CategoryRequest request) {

        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing == null) {
            ApiResponse<Category> errorResponse = ApiResponse.error("Category with id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        existing.setName(request.getName());
        try {
            Category updated = categoryRepository.save(existing);
            return ResponseEntity.ok(ApiResponse.ok("Category updated successfully", updated));
        } catch (Exception e) {
            ApiResponse<Category> errorResponse = ApiResponse.error("Category name may already exist");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Hapus kategori", description = "Menghapus kategori berdasarkan ID")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @Parameter(description = "ID kategori") @PathVariable int id) {

        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            ApiResponse<Void> errorResponse = ApiResponse.error("Category with id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        categoryRepository.delete(category);
        ApiResponse<Void> successResponse = ApiResponse.ok("Category deleted successfully", null);
        return ResponseEntity.ok(successResponse);
    }
}
