package com.hend.store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService {

    private static final Logger log = LoggerFactory.getLogger(ImageStorageService.class);
    private static final String PLACEHOLDER_IMAGE = "https://placehold.co/600x400/e2e8f0/94a3b8?text=No+Image";

    private final CloudinaryService cloudinaryService;
    private final LocalStorageService localStorageService;
    private final boolean cloudinaryAvailable;

    public ImageStorageService(CloudinaryService cloudinaryService,
                                LocalStorageService localStorageService) {
        this.cloudinaryService = cloudinaryService;
        this.localStorageService = localStorageService;
        this.cloudinaryAvailable = cloudinaryService.isAvailable();
        if (cloudinaryAvailable) {
            log.info("Cloudinary aktif → gambar disimpan ke cloud");
        } else {
            log.info("Cloudinary tidak aktif → gambar disimpan lokal");
        }
    }

    public String upload(MultipartFile file, String folder) {
        if (cloudinaryAvailable) {
            try {
                return cloudinaryService.upload(file, folder);
            } catch (Exception e) {
                log.warn("Cloudinary gagal, fallback ke local: {}", e.getMessage());
            }
        }
        return localStorageService.upload(file, folder);
    }

    public void delete(String fileUrl) {
        if (fileUrl == null || fileUrl.equals(PLACEHOLDER_IMAGE)) return;

        if (fileUrl.startsWith("http") && cloudinaryAvailable) {
            try {
                cloudinaryService.delete(fileUrl);
            } catch (Exception ignored) {}
        } else if (fileUrl.startsWith("/uploads/")) {
            localStorageService.delete(fileUrl);
        }
    }

    public String getPlaceholder() {
        return PLACEHOLDER_IMAGE;
    }
}
