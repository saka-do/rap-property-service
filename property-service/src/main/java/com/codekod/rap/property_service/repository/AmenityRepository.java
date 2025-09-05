package com.codekod.rap.property_service.repository;

import com.codekod.rap.property_service.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
