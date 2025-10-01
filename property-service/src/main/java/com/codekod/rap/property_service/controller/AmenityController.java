package com.codekod.rap.property_service.controller;


import com.codekod.rap.property_service.dto.AmenityDto;
import com.codekod.rap.property_service.service.AmenityService;
import com.codekod.rap.property_service.service.impl.AmenityServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService){
        this.amenityService = amenityService;
    }

    @GetMapping
    public ResponseEntity<List<AmenityDto>> fetchAllAmenities(){
        return ResponseEntity.ok(
                amenityService.fetchAllAmenities()
        );
    }

    @GetMapping("/lookup")
    public ResponseEntity<List<AmenityDto>> fetchAmenityByIds(@RequestParam List<Long> ids){
        return ResponseEntity.ok(
                amenityService.getAllAmenitiesByIds(ids)
        );
    }
}
