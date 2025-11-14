package com.system.trailer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.trailer.entity.Trailer;
import com.system.trailer.repository.TrailerRepository;

@Service
public interface TrailerService {

    public Trailer registerTrailer(Trailer trailer);
    
    public Optional<Trailer> getTrailerById(Long id);

    public Trailer updateInspectionStatus(Long id, boolean inspected);

    public Trailer approveTrailer(Long id,String approvedBy) ;

    public List<Trailer> getApprovalHistory() ;
}
