package com.codekod.rap.property_service.controller;

import com.codekod.rap.property_service.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/properties/{imgId}")
    public ResponseEntity<String> getImageUrl(@PathVariable long imgId) {
        String url = imageService.getImageUrl(imgId);
        System.out.println(url);
        return ResponseEntity.ok(url);
    }

}
