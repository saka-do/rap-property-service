package com.codekod.rap.property_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

    public String uploadObject(MultipartFile file, String bucketName, String key);
    String generatePresignedUrl(String objectKey, String bucketName, long durationMinutes);
    byte[] downloadObject(String objectKey, String bucketName);
    void deleteObject(String objectKey, String bucketName);

}
