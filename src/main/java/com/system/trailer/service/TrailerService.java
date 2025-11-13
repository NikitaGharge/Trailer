package com.system.trailer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.trailer.entity.Trailer;
import com.system.trailer.repository.TrailerRepository;

@Service
public class TrailerService {
    @Autowired
    private TrailerRepository trailerRepository;

    public Trailer registerTrailer(Trailer trailer) {
        return trailerRepository.save(trailer);
    }
    
    public Optional<Trailer> getTrailerById(Long id) {
        return trailerRepository.findById(id);
    }

    public Trailer updateInspectionStatus(Long id, boolean inspected) {
        Trailer trailer = trailerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trailer not found"));
        trailer.setInspected(inspected);
        return trailerRepository.save(trailer);
    }

    public Trailer approveTrailer(Long id) {
        Trailer trailer = trailerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trailer not found"));
        trailer.setApproved(true);
        return trailerRepository.save(trailer);
    }

    public List<Trailer> getApprovalHistory() {
    	return trailerRepository.findAll();
    }
}
