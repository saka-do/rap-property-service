package com.codekod.rap.property_service.dto;

//based on the imageID we will fetch the url and stream Image in different api with bodyonly as image
//used to update, create Image
public record ImageDto(
        Long propertyId,
        Long imageId,
        String imgUrl
) {
}
