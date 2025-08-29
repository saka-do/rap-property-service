package com.codekod.rap.property_service.repository;
import com.codekod.rap.property_service.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, String> {
}
