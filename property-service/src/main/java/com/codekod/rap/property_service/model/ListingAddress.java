package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "listing_address")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ListingAddress{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "street", length = 50)
    private String street;

    @Column(name="city", length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "postal_code", length = 50)
    private String postalCode;

//    JoinColumn(name = FK in current Entity; referencedCOlumnName= PK in FOreign Entity)
    @OneToOne
    @JoinColumn(name = "listing_id", referencedColumnName = "listing_id", nullable = false, unique = true)
    private Listing listing;

}
