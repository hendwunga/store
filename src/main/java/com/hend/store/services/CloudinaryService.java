package com.hend.store.services;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(@Autowired(required = false) Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public boolean isAvailable() {
        return cloudinary != null;
    }

    public String upload(MultipartFile file, String folder) {
        if (cloudinary == null) {
            throw new IllegalStateException("Cloudinary tidak dikonfigurasi");
        }
        try {
            Map<String, String> options = Map.of("folder", folder);
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);
            return result.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Cloudinary upload gagal: " + e.getMessage(), e);
        }
    }

    public void delete(String imageUrl) {
        if (cloudinary == null || imageUrl == null) return;
        try {
            String publicId = extractPublicId(imageUrl);
            if (publicId != null) {
                cloudinary.uploader().destroy(publicId, Map.of());
            }
        } catch (Exception ignored) {}
    }

    private String extractPublicId(String imageUrl) {
        if (!imageUrl.contains("cloudinary.com")) return null;
        String afterUpload = imageUrl.substring(imageUrl.indexOf("/upload/") + 8);
        int lastDot = afterUpload.lastIndexOf(".");
        return lastDot > 0 ? afterUpload.substring(0, lastDot) : afterUpload;
    }
}
