package com.codekod.rap.property_service.repository;

import com.codekod.rap.property_service.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
