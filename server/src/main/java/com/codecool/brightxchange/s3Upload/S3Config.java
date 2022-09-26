package com.codecool.brightxchange.s3Upload;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.s3")
public class S3Config {
    private String id;
    private String key;
    private String name;
    private String url;

    public S3Config() {
    }

    @Bean
    public AmazonS3 getS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(id, key);
        AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(credentials);

        return AmazonS3Client.builder().withRegion(Regions.EU_CENTRAL_1).withCredentials(provider).build();
    }
}
