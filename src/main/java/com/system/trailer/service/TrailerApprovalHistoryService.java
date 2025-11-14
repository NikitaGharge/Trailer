package com.system.trailer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.system.trailer.entity.TrailerApprovalHistory;
@Service
public interface TrailerApprovalHistoryService {
	  public List<TrailerApprovalHistory> getHistory(
	            String serialNumber,
	            Boolean approved,
	            String sortBy,
	            String direction
	    ) ;
}
