package com.system.trailer.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;
    private String model;
    private LocalDate productionDate;
    private boolean inspected;
    private boolean approved;
    private TrailerType type; // Enum for lift/no lift

    // getters and setters
}

 enum TrailerType {
    WITH_LIFT,
    WITHOUT_LIFT;
}
