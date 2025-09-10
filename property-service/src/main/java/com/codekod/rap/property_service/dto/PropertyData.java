package com.codekod.rap.property_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * dto used to create property data
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PropertyData {
    private String name;
    private String description;
    private BigDecimal price;
    private String owner;
    private List<ImageDto> imagesData;
    private List<Long> amenityIds;
    private AddressDto address;
}
