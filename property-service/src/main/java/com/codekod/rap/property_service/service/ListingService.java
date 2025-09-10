package com.codekod.rap.property_service.service;


import com.codekod.rap.property_service.repository.ListingRepository;

public class ListingService {

    private ListingRepository listingRepo;

    ListingService(ListingRepository listingRepo){
        this.listingRepo = listingRepo;
    }

}
