package com.system.trailer.service;

import java.util.List;

import org.springframework.stereotype.Component;
import com.system.trailer.entity.TrailerApprovalHistory;
@Component
public interface TrailerApprovalHistoryService {
	  public List<TrailerApprovalHistory> getHistory(
	            String serialNumber,
	            Boolean approved,
	            String sortBy,
	            String direction
	    ) ;
}
