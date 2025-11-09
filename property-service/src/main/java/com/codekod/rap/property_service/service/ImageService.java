package com.codekod.rap.property_service.service;

import com.codekod.rap.property_service.dto.ImageDto;
import com.codekod.rap.property_service.model.Listing;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImage(MultipartFile file, Listing listing);
    ImageDto addImage(MultipartFile file, Long propertyId);
    String getImageUrl(long propertyId);
    void deleteImage(long propertyId, long imgId);
}
