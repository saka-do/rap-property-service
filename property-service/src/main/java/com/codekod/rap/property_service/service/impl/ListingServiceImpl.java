package com.codekod.rap.property_service.service.impl;


import com.codekod.rap.property_service.dto.AddressDto;
import com.codekod.rap.property_service.dto.PropertyData;
import com.codekod.rap.property_service.dto.PropertyDetails;
import com.codekod.rap.property_service.model.Amenity;
import com.codekod.rap.property_service.model.Listing;
import com.codekod.rap.property_service.model.ListingAddress;
import com.codekod.rap.property_service.model.ListingImage;
import com.codekod.rap.property_service.repository.ListingRepository;
import com.codekod.rap.property_service.service.ImageService;
import com.codekod.rap.property_service.service.ListingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class ListingServiceImpl implements ListingService {

    private ListingRepository listingRepo;
    private final ImageService imageService;

    @Autowired
    ListingServiceImpl(ListingRepository listingRepo, ImageService imageService){
        this.imageService = imageService;
        this.listingRepo = listingRepo;
    }

    @Transactional
    public Long createProperty(PropertyData property, MultipartFile file){
        Listing listing = new Listing();
        listing.setName(property.getName());
        listing.setDescription(property.getDescription());
        listing.setOwner(property.getOwner());
        listing.setPrice(property.getPrice());

        ListingAddress listingAddress = new ListingAddress();
        listingAddress.setCity(property.getCity());

        // Set bidirectional relationship
        listingAddress.setListing(listing);
        listing.setListingAddress(listingAddress);

        listing = listingRepo.save(listing);

        String s3Key = this.imageService.saveImage(file, listing);
        log.info("Image uploaded for listing id: {}, s3Key, {}", listing.getId(), s3Key);
        listing = listingRepo.save(listing);
        return listing.getId();
    }


    public PropertyDetails updateProperty(Long listingId, PropertyDetails property){
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

        return convertListingToPropertyDetails(listing, img -> true);
    }

    public PropertyDetails getPropertyDetails(Long listingId){
        Listing listing = listingRepo.findById(listingId).orElseThrow();
        return convertListingToPropertyDetails(listing, img -> true); // while working with single prop, no image should filter
    }

    @Override
    public List<PropertyDetails> getAllProperties() {
        List<PropertyDetails> properties = new ArrayList<>();
        Predicate<ListingImage> imageFilter = ListingImage::getIsPrimary;
        properties = listingRepo.findAll().stream()
                .map(listing -> convertListingToPropertyDetails(listing, imageFilter))
                .toList();
        return properties;
    }

    @Override
    public void deleteProperty(Long listingId) {
        if(!listingRepo.existsById(listingId)){
            throw new RuntimeException("Listing not found with id: " + listingId);
        }
        listingRepo.deleteById(listingId);
    }

    private PropertyDetails convertListingToPropertyDetails(Listing listing, Predicate<ListingImage> imageFilter){
        ListingAddress listingAddress = listing.getListingAddress();
        AddressDto addressDto = AddressDto.builder()
                .street(listingAddress.getStreet())
                .city(listingAddress.getCity())
                .postalCode(listingAddress.getPostalCode())
                .state(listingAddress.getState())
                .country(listingAddress.getCountry())
                .build();

        return PropertyDetails.builder()
                .propertyId(listing.getId())
                .name(listing.getName())
                .description(listing.getDescription())
                .address(addressDto)
                .owner(listing.getOwner())
                .price(listing.getPrice())
                .imageIds(listing.getImages().stream().filter(imageFilter).map(ListingImage::getImageId).toList())
                .amenityIds(listing.getAmenities().stream().map(Amenity::getAmenityId).toList())
                .build();
    }

}
