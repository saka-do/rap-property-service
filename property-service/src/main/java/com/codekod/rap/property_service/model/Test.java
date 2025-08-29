package com.codekod.rap.property_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @Data
@NoArgsConstructor
public class Test {

    @Id
    private String name;

    private String description;
}
