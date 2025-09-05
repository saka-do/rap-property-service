package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Table(name = "listing_images")
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image", nullable = false, columnDefinition = "BLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "listing_id", referencedColumnName = "listing_id", nullable = false)
    private Listing listing;


}
