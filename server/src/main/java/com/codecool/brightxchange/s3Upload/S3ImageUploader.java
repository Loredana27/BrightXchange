package com.codecool.brightxchange.s3Upload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.codecool.brightxchange.model.CategoryImage;
import com.codecool.brightxchange.model.ProductImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ResultOfMethodCallIgnored")
@Service
public class S3ImageUploader {
    private final AmazonS3 s3Client;
    private final S3Config config;
    private final List<File> productImages;
    private File categoryImage;

    public S3ImageUploader(AmazonS3 s3Client, S3Config config) {
        this.s3Client = s3Client;
        this.config = config;
        productImages = new ArrayList<>();

    }

    public List<ProductImage> uploadProductImages(String fileName) {
        List<ProductImage> images = new ArrayList<>();
        for (int i = 0; i < productImages.size(); i++) {
            String imageName = String.format("%s_%s.jpg", fileName, i);

            PutObjectRequest request = new PutObjectRequest(config.getName(), imageName, productImages.get(i));
            s3Client.putObject(request);

            productImages.get(i).delete();
            images.add(new ProductImage(config.getUrl() + imageName.replace(" ", "%20")));
        }

        productImages.clear();
        return images;
    }

    public CategoryImage uploadCategoryImage(String fileName) {
        String imageName = String.format(fileName + ".jpg");
        PutObjectRequest request = new PutObjectRequest(config.getName(), imageName, categoryImage);
        s3Client.putObject(request);
        clearImages();
        return new CategoryImage(config.getUrl() + imageName.replace(" ","%20"));
    }

    public void saveCategoryImage(MultipartFile image) {
        File file1 = new File("img.jpg");
        try(FileOutputStream out = new FileOutputStream(file1)) {
            out.write(image.getInputStream().readAllBytes());
            file1.createNewFile();
            categoryImage = file1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveProductImages(MultipartFile image) {
        File file1 = new File(String.format("img%s.jpg", productImages.size()));
        try(FileOutputStream out = new FileOutputStream(file1)) {
            out.write(image.getInputStream().readAllBytes());
            file1.createNewFile();
            productImages.add(file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(productImages);
    }

    public void clearImages() {
        if(categoryImage != null) {
            categoryImage.delete();
            categoryImage = null;
        }
        productImages.forEach(File::delete);
        productImages.clear();
    }
}
