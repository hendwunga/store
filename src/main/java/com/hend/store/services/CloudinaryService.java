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

    public String upload(MultipartFile file, String folder) {
        if (cloudinary == null) {
            throw new IllegalStateException(
                "Cloudinary is not configured. Set CLOUDINARY_CLOUD_NAME, " +
                "CLOUDINARY_API_KEY, and CLOUDINARY_API_SECRET environment variables."
            );
        }
        try {
            Map<String, String> options = Map.of("folder", folder);
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }
}
