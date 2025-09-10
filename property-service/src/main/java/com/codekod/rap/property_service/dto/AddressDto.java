package com.codekod.rap.property_service.dto;

import lombok.Builder;

@Builder
public record AddressDto(
        String street,
        String city,
        String state,
        String country,
        String postalCode
) {}
