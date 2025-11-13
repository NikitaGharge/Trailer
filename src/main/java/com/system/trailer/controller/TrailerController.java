package com.system.trailer.controller;

import java.util.List;
import java.util.Optional;

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

import com.system.trailer.dto.TrailerDTO;
import com.system.trailer.entity.Trailer;
import com.system.trailer.service.TrailerService;

@RestController
@RequestMapping("/trailers")
public class TrailerController {

    @Autowired
    private TrailerService trailerService;

    @PostMapping
    public ResponseEntity<Trailer> registerTrailer(@RequestBody Trailer trailer) {
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

    @PutMapping("/{id}/approval")
    public ResponseEntity<Trailer> approveTrailer(@PathVariable Long id) {
        Trailer trailer = trailerService.approveTrailer(id);
        return ResponseEntity.ok(trailer);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Trailer>> getApprovalHistory() {
        List<Trailer> history = trailerService.getApprovalHistory();
        return ResponseEntity.ok(history);
    }
}
