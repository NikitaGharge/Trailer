package com.system.trailer.dto;

import java.time.LocalDate;

import com.system.trailer.entity.TrailerType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class TrailerRequestDTO {

    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Production date is required")
    private LocalDate productionDate;

    @NotNull(message = "Trailer type is required")
    private TrailerType trailerType;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

	public TrailerType getTrailerType() {
		return trailerType;
	}

	public void setTrailerType(TrailerType trailerType) {
		this.trailerType = trailerType;
	}

    // No inspected/approved here — backend handles that
    
}
