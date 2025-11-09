package com.codekod.rap.property_service.controller;

import com.codekod.rap.property_service.dto.ImageDto;
import com.codekod.rap.property_service.service.ImageService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/properties/{propertyId}")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{imgId}")
    public ResponseEntity<String> getImageUrl(@PathVariable long imgId) {
        String url = imageService.getImageUrl(imgId);
        System.out.println(url);
        return ResponseEntity.ok(url);
    }

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> addImage(@PathVariable long propertyId,
                                             @RequestPart("image") MultipartFile image){
        return new ResponseEntity<ImageDto>(imageService.addImage(image,propertyId),HttpStatus.CREATED);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> removeImage(@PathVariable long propertyId, @PathVariable long imageId){
        imageService.deleteImage(propertyId, imageId);
        return ResponseEntity.noContent().build();
    }

}
