package com.codekod.rap.property_service.service;

import com.codekod.rap.property_service.dto.PropertyData;
import com.codekod.rap.property_service.dto.PropertyDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListingService {

    public Long createProperty(PropertyData propertyData, MultipartFile file);

    public PropertyDetails updateProperty(Long propertyId,PropertyDetails propertyData);

    public PropertyDetails getPropertyDetails(Long listingId);

    public List<PropertyDetails> getAllProperties();

    public void deleteProperty(Long listingId);
}
