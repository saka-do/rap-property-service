package com.codekod.rap.property_service.controller;


import com.codekod.rap.property_service.dto.PropertyData;
import com.codekod.rap.property_service.dto.PropertyDetails;
import com.codekod.rap.property_service.service.ListingService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {


    private final ListingService listingService;

    @Autowired
    PropertyController(ListingService listingService){
        this.listingService = listingService;
    }


    @GetMapping
    public ResponseEntity<List<PropertyDetails>> getAllProperties(){
        return ResponseEntity.ok(
                listingService.getAllProperties()
        );
    }
    /**
     * Create Property
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveProperty(@RequestPart("image")MultipartFile imageFile, @RequestPart("propertyData") PropertyData propertyData){
        System.out.println(imageFile.getName() + " " + imageFile.getSize());
        Long propertyId = listingService.createProperty(propertyData, imageFile);
        return new ResponseEntity<Long>(propertyId, HttpStatus.CREATED);
    }

    /**
     * Read Property by Id
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<PropertyDetails> fetchProperty(@PathVariable Long propertyId){
        return ResponseEntity.ok(
                listingService.getPropertyDetails(propertyId)
        );
    }
    /**
     * Delete Property by Id
     */
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long propertyId){
        listingService.deleteProperty(propertyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/property/{propertyId}")
    public ResponseEntity<PropertyDetails> updateProperty(@PathVariable Long propertyId, @RequestBody PropertyDetails propertyData){
        return ResponseEntity.ok(
                listingService.updateProperty(propertyId, propertyData)
        );
    }
}
