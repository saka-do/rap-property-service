package com.codekod.rap.property_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Used to get property details
 */
@Builder @Getter @Setter
public class PropertyDetails {

    private Long propertyId;
    private String name;
    private String description;
    private BigDecimal price;
    private String owner;
    private List<Long> imageIds;
    private List<Long> amenityIds;
    private AddressDto address;
}
