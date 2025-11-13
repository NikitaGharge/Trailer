package com.system.trailer.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class TrailerDTO {
    private String serialNumber;
    private String model;
    private LocalDate productionDate;
    private TrailerType type;

    // getters and setters
}

enum TrailerType {
    WITH_LIFT,
    WITHOUT_LIFT;
}