package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "listings")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Listing extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private Long id;

    @Column(name = "listing_name")
    private String name;

    @Column(name = "listing_description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "owner_name")
    private String owner;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "listing")
    private ListingAddress listingAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "listing", orphanRemoval = true)
    private List<ListingImage> images = new ArrayList<ListingImage>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "lisitngs_amenities",
                joinColumns = @JoinColumn(name = "listing_id"),
                inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities = new ArrayList<>();

    public void addListingImage(ListingImage listImage){
        images.add(listImage);
        listImage.setListing(this);
    }

    public void removeListingImage(ListingImage image){
        images.remove(image);
        image.setListing(null);
    }

    public void addAmenity(Amenity amenity){
        amenities.add(amenity);
        amenity.getListings().add(this);
    }

    public void removeAmenity(Amenity amenity){
        amenities.remove(amenity);
        amenity.getListings().remove(this);
    }

}
