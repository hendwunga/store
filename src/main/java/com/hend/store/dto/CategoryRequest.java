package com.hend.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request body untuk membuat atau mengupdate kategori")
public class CategoryRequest {

    @Schema(description = "Nama kategori", example = "Electronics", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Category name is required")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
