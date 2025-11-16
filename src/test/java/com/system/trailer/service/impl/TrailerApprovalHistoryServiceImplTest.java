package com.system.trailer.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Sort;

import com.system.trailer.entity.TrailerApprovalHistory;
import com.system.trailer.repository.TrailerApprovalHistoryRepository;

class TrailerApprovalHistoryServiceImplTest {

    @InjectMocks
    private TrailerApprovalHistoryServiceImpl service;

    @Mock
    private TrailerApprovalHistoryRepository repository;

    private TrailerApprovalHistory history;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        history = new TrailerApprovalHistory();
        history.setApproved(true);
    }

    // ----------------------------------------------------------------------
    // WHEN approved != null → expect repository.findByTrailerSerialNumberAndApproved()
    // ----------------------------------------------------------------------
    @Test
    void testGetHistory_WithApprovedFilter() {
        String serial = "TR123";
        Boolean approved = true;
        String sortBy = "approvalDate";
        String direction = "DESC";

        Sort expectedSort = Sort.by(Sort.Direction.DESC, sortBy);

        when(repository.findByTrailerSerialNumberAndApproved(serial, approved, expectedSort))
                .thenReturn(List.of(history));

        List<TrailerApprovalHistory> result =
                service.getHistory(serial, approved, sortBy, direction);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isApproved());

        verify(repository, times(1))
                .findByTrailerSerialNumberAndApproved(serial, approved, expectedSort);

        verify(repository, never()).findByTrailerSerialNumber(anyString(), any());
    }

    // ----------------------------------------------------------------------
    // WHEN approved == null → expect repository.findByTrailerSerialNumber()
    // ----------------------------------------------------------------------
    @Test
    void testGetHistory_WithoutApprovedFilter() {
        String serial = "TR123";
        Boolean approved = null;
        String sortBy = "approvalDate";
        String direction = "ASC";

        Sort expectedSort = Sort.by(Sort.Direction.ASC, sortBy);

        when(repository.findByTrailerSerialNumber(serial, expectedSort))
                .thenReturn(List.of(history));

        List<TrailerApprovalHistory> result =
                service.getHistory(serial, approved, sortBy, direction);

        assertEquals(1, result.size());

        verify(repository, times(1))
                .findByTrailerSerialNumber(serial, expectedSort);

        verify(repository, never())
                .findByTrailerSerialNumberAndApproved(anyString(), anyBoolean(), any());
    }

    // ----------------------------------------------------------------------
    // Validate invalid sort direction (should throw exception)
    // ----------------------------------------------------------------------
    @Test
    void testGetHistory_InvalidDirection() {
        String serial = "TR123";
        Boolean approved = null;
        String sortBy = "approvalDate";
        String direction = "INVALID";

        assertThrows(IllegalArgumentException.class, () -> {
            service.getHistory(serial, approved, sortBy, direction);
        });

        verifyNoInteractions(repository);
    }
}
