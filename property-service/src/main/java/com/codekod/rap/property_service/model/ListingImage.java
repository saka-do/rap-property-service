package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "listing_images")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image", nullable = false, columnDefinition = "BLOB")
    private byte[] image;


}
