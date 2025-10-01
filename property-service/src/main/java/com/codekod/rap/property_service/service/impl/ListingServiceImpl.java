package com.codekod.rap.property_service.service.impl;


import com.codekod.rap.property_service.dto.AddressDto;
import com.codekod.rap.property_service.dto.PropertyData;
import com.codekod.rap.property_service.dto.PropertyDetails;
import com.codekod.rap.property_service.model.Amenity;
import com.codekod.rap.property_service.model.Listing;
import com.codekod.rap.property_service.model.ListingAddress;
import com.codekod.rap.property_service.repository.ListingRepository;
import com.codekod.rap.property_service.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingServiceImpl implements ListingService {

    private ListingRepository listingRepo;

    @Autowired
    ListingServiceImpl(ListingRepository listingRepo){
        this.listingRepo = listingRepo;
    }

    @Transactional
    public Long createProperty(PropertyData property){
        Listing listing = new Listing();
        listing.setName(property.getName());
        listing.setDescription(property.getDescription());
        listing.setOwner(property.getOwner());
        listing.setPrice(property.getPrice());

        ListingAddress listingAddress = new ListingAddress();
        listingAddress.setCity(property.getAddress().city());
        listingAddress.setStreet(property.getAddress().street());
        listingAddress.setState(property.getAddress().state());
        listingAddress.setPostalCode(property.getAddress().postalCode());
        listingAddress.setCountry(property.getAddress().country());

        // Set bidirectional relationship
        listingAddress.setListing(listing);
        listing.setListingAddress(listingAddress);

        listing = listingRepo.save(listing);

        return listing.getId();
    }


    public PropertyDetails updateProperty(Long listingId, PropertyData property){
        Listing listing = listingRepo.findById(listingId).orElseThrow(()-> new RuntimeException("Listing not found with id: " + listingId));

        listing.setName(property.getName());
        listing.setDescription(property.getDescription());
        listing.setOwner(property.getOwner());
        listing.setPrice(property.getPrice());

        ListingAddress listingAddress = listing.getListingAddress();
        listingAddress.setCity(property.getAddress().city());
        listingAddress.setStreet(property.getAddress().street());
        listingAddress.setState(property.getAddress().state());
        listingAddress.setPostalCode(property.getAddress().postalCode());
        listingAddress.setCountry(property.getAddress().country());

        listing = listingRepo.save(listing);

        return convertListingToPropertyDetails(listing);
    }

    public PropertyDetails getPropertyDetails(Long listingId){
        Listing listing = listingRepo.findById(listingId).orElseThrow();
        return convertListingToPropertyDetails(listing);
    }

    @Override
    public List<PropertyDetails> getAllProperties() {
        List<PropertyDetails> properties = new ArrayList<>();
        properties = listingRepo.findAll().stream().map((this::convertListingToPropertyDetails)).toList();
        return properties;
    }

    @Override
    public void deleteProperty(Long listingId) {
        if(!listingRepo.existsById(listingId)){
            throw new RuntimeException("Listing not found with id: " + listingId);
        }
        listingRepo.deleteById(listingId);
    }

    private PropertyDetails convertListingToPropertyDetails(Listing listing){
        ListingAddress listingAddress = listing.getListingAddress();
        AddressDto addressDto = AddressDto.builder()
                .street(listingAddress.getStreet())
                .city(listingAddress.getCity())
                .postalCode(listingAddress.getPostalCode())
                .state(listingAddress.getState())
                .country(listingAddress.getCountry())
                .build();

        return PropertyDetails.builder()
                .property_id(listing.getId())
                .name(listing.getName())
                .description(listing.getDescription())
                .address(addressDto)
                .owner(listing.getOwner())
                .price(listing.getPrice())
                .imageIds(new ArrayList<>())
                .amenityIds(listing.getAmenities().stream().map(Amenity::getAmenityId).toList())
                .build();
    }

}
