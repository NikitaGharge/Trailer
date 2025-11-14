package com.system.trailer.dto;

import java.time.LocalDate;

import com.system.trailer.entity.TrailerType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class TrailerResponseDTO {

	private Long id;
    private String serialNumber;
    private String model;
    private LocalDate productionDate;
    private boolean inspected;
    private boolean approved;
    private TrailerType trailerType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public boolean isInspected() {
		return inspected;
	}
	public void setInspected(boolean inspected) {
		this.inspected = inspected;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public TrailerType getTrailerType() {
		return trailerType;
	}
	public void setTrailerType(TrailerType trailerType) {
		this.trailerType = trailerType;
	}
    
    
}
