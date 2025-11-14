package com.system.trailer.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.trailer.entity.Trailer;
import com.system.trailer.entity.TrailerApprovalHistory;
import com.system.trailer.repository.TrailerApprovalHistoryRepository;
import com.system.trailer.repository.TrailerRepository;
import com.system.trailer.service.TrailerService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class TrailerServiceImpl implements TrailerService {
    @Autowired
    private TrailerRepository trailerRepository;
    
    @Autowired
    private TrailerApprovalHistoryRepository historyRepository;

    public Trailer registerTrailer(Trailer trailer) {
        return trailerRepository.save(trailer);
    }
    
    public Optional<Trailer> getTrailerById(Long id) {
        //log.info("Processing trailer with serial number: {}", id);
        return trailerRepository.findById(id);
    }

    public Trailer updateInspectionStatus(Long id, boolean inspected) {
        Trailer trailer = trailerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trailer not found"));
        trailer.setInspected(inspected);
        return trailerRepository.save(trailer);
    }

    public Trailer approveTrailer(Long id,String approvedBy) {
        Trailer trailer = trailerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trailer not found"));
        trailer.setApproved(true);
        trailerRepository.save(trailer);

     // Insert history record
        TrailerApprovalHistory history = new TrailerApprovalHistory();
        history.setTrailer(trailer);
        history.setApproved(true);
        history.setApprovalDate(LocalDateTime.now());
        history.setApprovedBy(approvedBy);
        historyRepository.save(history);

      //  log.info("Trailer {} approved by {}", trailer.getSerialNumber(), approvedBy);

        return trailer;
    }

    public List<Trailer> getApprovalHistory() {
    	return trailerRepository.findAll();
    }
}
