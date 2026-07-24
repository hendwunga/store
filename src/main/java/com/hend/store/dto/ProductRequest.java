package com.hend.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body untuk membuat atau mengupdate produk")
public class ProductRequest {

    @Schema(description = "Nama produk", example = "MacBook Pro 14 inch", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Name is required")
    private String name;

    @Schema(description = "Merek produk", example = "Apple", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Brand is required")
    private String brand;

    @Schema(description = "ID kategori produk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 1, message = "Category is required")
    private int categoryId;

    @Schema(description = "Harga produk", example = "19999000", minimum = "0")
    @Min(0)
    private double price;

    @Schema(description = "Deskripsi produk (10-2000 karakter)", example = "MacBook Pro 14 inch dengan chip M3 Pro", minLength = 10, maxLength = 2000)
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
    private String description;

    @Schema(description = "URL gambar produk", example = "https://example.com/macbook.jpg")
    private String imageUrl;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
