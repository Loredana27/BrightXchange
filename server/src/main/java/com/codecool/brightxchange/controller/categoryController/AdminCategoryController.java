package com.codecool.brightxchange.controller.categoryController;

import com.codecool.brightxchange.model.Category;
import com.codecool.brightxchange.model.CategoryImage;
import com.codecool.brightxchange.model.CategorySpec;
import com.codecool.brightxchange.s3Upload.S3ImageUploader;
import com.codecool.brightxchange.service.CategoryImageService;
import com.codecool.brightxchange.service.CategoryService;
import com.codecool.brightxchange.service.CategorySpecService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {
    private final CategoryService service;
    private final CategoryImageService imageService;
    private final CategorySpecService specService;
    private final S3ImageUploader uploader;


    public AdminCategoryController(CategoryService service,
                                   CategoryImageService imageService,
                                   CategorySpecService specService,
                                   S3ImageUploader uploader) {
        this.service = service;
        this.imageService = imageService;
        this.specService = specService;
        this.uploader = uploader;
    }

    @PostMapping
    private Category add(@RequestBody Category category) {
        System.out.println(category);
        List<CategorySpec> categorySpecs = category.getCategorySpecs();
        category.setCategorySpecs(new ArrayList<>());
        // update category
        category = service.saveAndFlush(category);
        // save category specs
        for (CategorySpec categorySpec : categorySpecs) {
            categorySpec.setCategory(category);
        }


        category.setCategorySpecs(specService.saveAllAndFlush(categorySpecs));
        // upload Image
        System.out.println("start upload");
        CategoryImage categoryImage = uploader.uploadCategoryImage(category.getImageName());
        System.out.println("end upload");
        System.out.println(categoryImage);
        // add bound
//        categoryImage.setCategory(category);
        // save image details and update category

        category.setImage(imageService.saveAndFlush(categoryImage));

        service.saveAndFlush(category);
        System.out.println(category);

        return category;
    }


}
