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

    private Long property_id;
    private String name;
    private String description;
    private BigDecimal price;
    private String owner;
    private String thumbnailImage; //base64 encoded image for thumbnail
    private List<Long> imageIds;
    private List<Long> amenityIds;
    private AddressDto address;
}
