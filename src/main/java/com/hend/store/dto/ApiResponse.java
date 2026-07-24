package com.hend.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response wrapper untuk semua API response")
public class ApiResponse<T> {

    @Schema(description = "Status operasi", example = "true")
    private boolean success;

    @Schema(description = "Pesan deskriptif", example = "Product created successfully")
    private String message;

    @Schema(description = "Data yang dikembalikan")
    private T data;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
