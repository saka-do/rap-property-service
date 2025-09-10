package com.codekod.rap.property_service.controller;


import com.codekod.rap.property_service.dto.AmenityDto;
import com.codekod.rap.property_service.service.AmenityServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/amenities")
public class AmenityController {

    private AmenityServiceImpl amenityService;

    public AmenityController(AmenityServiceImpl amenityService){
        this.amenityService = amenityService;
    }

    public ResponseEntity<List<AmenityDto>> fetchAllAmenities(){
        return ResponseEntity.ok(
                amenityService.fetchAllAmenities()
        );
    }

    public ResponseEntity<AmenityDto> fetchAmenityById(){
        return null;
    }
}
