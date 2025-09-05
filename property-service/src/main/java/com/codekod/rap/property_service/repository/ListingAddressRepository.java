package com.codekod.rap.property_service.repository;

import com.codekod.rap.property_service.model.ListingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingAddressRepository extends JpaRepository<ListingAddress, Long> {
}
