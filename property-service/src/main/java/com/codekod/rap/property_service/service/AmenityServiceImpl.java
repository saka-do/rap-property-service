package com.codekod.rap.property_service.service;

import com.codekod.rap.property_service.dto.AmenityDto;
import com.codekod.rap.property_service.model.Amenity;
import com.codekod.rap.property_service.repository.AmenityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmenityServiceImpl {

    private AmenityRepository amenityRepo;

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
}
