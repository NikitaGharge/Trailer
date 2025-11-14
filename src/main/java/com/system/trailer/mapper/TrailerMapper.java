package com.system.trailer.mapper;

import com.system.trailer.dto.TrailerRequestDTO;
import com.system.trailer.dto.TrailerResponseDTO;
import com.system.trailer.entity.Trailer;


public class TrailerMapper {

    public static Trailer toEntity(TrailerRequestDTO dto) {
        Trailer trailer = new Trailer();
        trailer.setSerialNumber(dto.getSerialNumber());
        trailer.setModel(dto.getModel());
        trailer.setProductionDate(dto.getProductionDate());
        trailer.setTrailerType(dto.getTrailerType());
        return trailer;
    }

    public static TrailerResponseDTO toDTO(Trailer trailer) {
        TrailerResponseDTO dto = new TrailerResponseDTO();
        dto.setId(trailer.getId());
        dto.setSerialNumber(trailer.getSerialNumber());
        dto.setModel(trailer.getModel());
        dto.setProductionDate(trailer.getProductionDate());
        dto.setTrailerType(trailer.getTrailerType());
        dto.setInspected(trailer.isInspected());
        dto.setApproved(trailer.isApproved());
        return dto;
    }
}
