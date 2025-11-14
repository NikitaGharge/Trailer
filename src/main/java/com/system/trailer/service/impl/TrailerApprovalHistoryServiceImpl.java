package com.system.trailer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.system.trailer.entity.TrailerApprovalHistory;
import com.system.trailer.repository.TrailerApprovalHistoryRepository;
import com.system.trailer.service.TrailerApprovalHistoryService;
@Service
public class TrailerApprovalHistoryServiceImpl implements TrailerApprovalHistoryService{
    @Autowired
    private TrailerApprovalHistoryRepository repository;

    public List<TrailerApprovalHistory> getHistory(
            String serialNumber,
            Boolean approved,
            String sortBy,
            String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);

        if (approved != null) {
            return repository.findByTrailerSerialNumberAndApproved(serialNumber, approved, sort);
        } else {
            return repository.findByTrailerSerialNumber(serialNumber, sort);
        }
    }

}
