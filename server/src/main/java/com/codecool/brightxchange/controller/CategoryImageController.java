package com.codecool.brightxchange.controller;

import com.codecool.brightxchange.s3Upload.S3ImageUploader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/category-images")
public class CategoryImageController {
    private final S3ImageUploader uploader;


    public CategoryImageController(S3ImageUploader uploader) {
        this.uploader = uploader;
    }

    @GetMapping("delete")
    public void clear() {
        uploader.clearImages();
    }

    @PostMapping
    public void imageUploader(@RequestParam("image") MultipartFile image) {
        uploader.saveCategoryImage(image);
    }

}
