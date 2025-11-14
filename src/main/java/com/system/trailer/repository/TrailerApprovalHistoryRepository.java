package com.system.trailer.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.system.trailer.entity.TrailerApprovalHistory;

public interface TrailerApprovalHistoryRepository extends JpaRepository<TrailerApprovalHistory, Long> {

    List<TrailerApprovalHistory> findByTrailerSerialNumberAndApproved(
        String serialNumber, boolean approved, Sort sort
    );

    List<TrailerApprovalHistory> findByTrailerSerialNumber(String serialNumber, Sort sort);

}
