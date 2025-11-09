package com.codekod.rap.property_service.service.impl;

import com.codekod.rap.property_service.dto.AmenityDto;
import com.codekod.rap.property_service.model.Amenity;
import com.codekod.rap.property_service.repository.AmenityRepository;
import com.codekod.rap.property_service.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepo;

    @Autowired
    AmenityServiceImpl(AmenityRepository amenityRepository){
        this.amenityRepo = amenityRepository;
    }


    public List<AmenityDto> fetchAllAmenities(){
        List<Amenity> amenityList = this.amenityRepo.findAll();
        List<AmenityDto> amenities = new ArrayList<>();
        if(!amenityList.isEmpty()){
            amenities = amenityList.stream()
                        .map(amenity -> new AmenityDto(amenity.getAmenityId(), amenity.getAmenityName()))
                        .toList();
        }

        return amenities;
    }

    public List<AmenityDto> getAllAmenitiesByIds(List<Long> amenityIds){
        List<Amenity> amenityList = this.amenityRepo.findAllById(amenityIds);
        List<AmenityDto> amenities = new ArrayList<>();
        if(!amenityList.isEmpty()){
            amenities = amenityList.stream()
                    .map(a -> new AmenityDto(a.getAmenityId(), a.getAmenityName()))
                    .toList();
        }

        return amenities;
    }

    public List<Amenity> loadAmenitiesByIds(List<Long> amenityIds){
        return this.amenityRepo.findAllById(amenityIds);
    }
}
