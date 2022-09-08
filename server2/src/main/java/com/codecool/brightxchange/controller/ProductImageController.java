package com.codecool.brightxchange.controller;

import com.codecool.brightxchange.s3Upload.S3ImageUploader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin/product-images")
public class ProductImageController {
    private final S3ImageUploader uploader;

    public ProductImageController(S3ImageUploader uploader) {
        this.uploader = uploader;
    }

    @PostMapping
    public void imageUploader(@RequestParam("image") MultipartFile image) {
        uploader.saveProductImages(image);
    }

    @DeleteMapping
    public void clear() {
        uploader.clearImages();
    }
}
