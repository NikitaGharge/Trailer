package com.system.trailer.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.system.trailer.dto.TrailerRequestDTO;
import com.system.trailer.entity.Trailer;
import com.system.trailer.entity.TrailerApprovalHistory;
import com.system.trailer.mapper.TrailerMapper;
import com.system.trailer.service.TrailerApprovalHistoryService;
import com.system.trailer.service.TrailerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/trailers")
public class TrailerController {

    @Autowired
    private TrailerService trailerService;
    

    @Autowired
    private TrailerApprovalHistoryService historyService;

    @PostMapping
    public ResponseEntity<Trailer> registerTrailer(@Valid @RequestBody TrailerRequestDTO requestDTO) {
        Trailer trailer = TrailerMapper.toEntity(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(trailerService.registerTrailer(trailer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trailer> getTrailer(@PathVariable Long id) {
    	return trailerService.getTrailerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/inspection")
    public ResponseEntity<Trailer> updateInspectionStatus(@PathVariable Long id, @RequestParam boolean inspected) {
        Trailer trailer = trailerService.updateInspectionStatus(id, inspected);
        return ResponseEntity.ok(trailer);
    }

    @PutMapping("/{id}/{approvedBy}/approval")
    public ResponseEntity<Trailer> approveTrailer(@PathVariable Long id, @PathVariable String approvedBy) {
        Trailer trailer = trailerService.approveTrailer(id,approvedBy);
        return ResponseEntity.ok(trailer);
    }

	/*
	 * @GetMapping("/history") public ResponseEntity<List<Trailer>>
	 * getApprovalHistory() { List<Trailer> history =
	 * trailerService.getApprovalHistory(); return ResponseEntity.ok(history); }
	 */
    
    @GetMapping("/history")
    public List<TrailerApprovalHistory> getHistory(
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) Boolean approved,
            @RequestParam(defaultValue = "approvalDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        return historyService.getHistory(serialNumber, approved, sortBy, direction);
    }
}
