package com.codekod.rap.property_service.controller;


import com.codekod.rap.property_service.dto.PropertyData;
import com.codekod.rap.property_service.dto.PropertyDetails;
import com.codekod.rap.property_service.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
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
    @PostMapping
    public ResponseEntity<Long> createProperty(@RequestBody PropertyData propertyData){
        return ResponseEntity.ok(
                listingService.createProperty(propertyData)
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

    @PutMapping("property/{propertyId}")
    public void updateProperty(@PathVariable Long propertyId, @RequestBody PropertyData propertyData){
        listingService.updateProperty(propertyId, propertyData);
    }
}
