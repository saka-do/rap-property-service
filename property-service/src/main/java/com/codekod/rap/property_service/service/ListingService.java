package com.codekod.rap.property_service.service;

import com.codekod.rap.property_service.dto.PropertyData;
import com.codekod.rap.property_service.dto.PropertyDetails;

import java.util.List;

public interface ListingService {

    public Long createProperty(PropertyData propertyData);
    public PropertyDetails updateProperty(Long propertyId,PropertyData propertyData);

    public PropertyDetails getPropertyDetails(Long listingId);

    public List<PropertyDetails> getAllProperties();

    public void deleteProperty(Long listingId);
}
