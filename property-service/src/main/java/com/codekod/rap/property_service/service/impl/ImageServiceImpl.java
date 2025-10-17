package com.codekod.rap.property_service.service.impl;

import com.codekod.rap.property_service.model.Listing;
import com.codekod.rap.property_service.model.ListingImage;
import com.codekod.rap.property_service.repository.ListingImageRepository;
import com.codekod.rap.property_service.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final AwsS3ImageServiceImpl awsS3ImageServiceImpl;
    private final ListingImageRepository listingImageRepository;

    @Value("${app.s3.bucket-name}")
    private String bucketName;

    @Value("${app.s3.base-prefix}")
    private String prefix;

    @Autowired
    public ImageServiceImpl(AwsS3ImageServiceImpl awsS3ImageServiceImpl, ListingImageRepository listingImageRepository) {
        this.awsS3ImageServiceImpl = awsS3ImageServiceImpl;
        this.listingImageRepository = listingImageRepository;
    }


    @Override
    public String saveImage(MultipartFile file, Listing listing) {
        String key = prefix+ "/"+ listing.getId()+ "/";
        log.info("Uploading image to S3 with key: {}", key);
        key = this.awsS3ImageServiceImpl.uploadObject(file, bucketName, key);
        log.info("Uploaded image to S3 with key: {}", key);
        // Save the S3 key to the database
        ListingImage listingImage = ListingImage.builder()
                .contentType(file.getContentType())
                .listing(listing)
                .s3Key(key)
                .fileName(file.getName())
                .isPrimary(true)
                .sizeBytes(file.getSize())
                .build();
        listing.addListingImage(listingImage);

        return key;
    }

    @Override
    public String getImageUrl(long imgId) {
        ListingImage listingImage = listingImageRepository.findById(imgId).
                orElseThrow(()-> new RuntimeException("Image not found with id: " + imgId));
        String s3Key = listingImage.getS3Key();
        return this.awsS3ImageServiceImpl.generatePresignedUrl(s3Key, bucketName, 5);
    }

    @Override
    public void deleteImage(long propertyId) {

    }
}
