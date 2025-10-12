package com.codekod.rap.property_service.service.impl;

import com.codekod.rap.property_service.service.AwsS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;

@Service
@Slf4j
public class AwsS3ImageServiceImpl implements AwsS3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    

    @Autowired
    public AwsS3ImageServiceImpl(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    @Override
    public String uploadObject(MultipartFile file, String bucketName, String key) {
        String originalFilename = file.getOriginalFilename();
        String ext = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        key = key+ (ext.isEmpty()? "" : ext);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .contentType(file.getContentType())
                .contentDisposition("inline")
                .key(key)
                .build();
        log.info("PutObjectRequest: {}", putObjectRequest.toString());
        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            String errorMsg = "Failed to upload file to S3";
            log.error(errorMsg);
            throw new RuntimeException("Failed to upload file to S3", e);
        }
        return key;
    }

    @Override
    public String generatePresignedUrl(String objectKey, String bucketName, long durationMinutes) {
        GetObjectRequest getObjReq = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        GetObjectPresignRequest preSignReq = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(durationMinutes))
                .getObjectRequest(getObjReq)
                .build();
        return s3Presigner.presignGetObject(preSignReq).url().toString();
    }

    @Override
    public byte[] downloadObject(String objectKey, String bucketName) {
        return new byte[0];
    }

    @Override
    public void deleteObject(String objectKey, String bucketName) {
        // Not implemented
    }
}
