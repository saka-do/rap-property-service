package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "listings")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ListingModel extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private Long id;

    @Column(name = "listing_name")
    private String name;

    @Column(name = "listing_description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "owner_name")
    private String owner;

}
