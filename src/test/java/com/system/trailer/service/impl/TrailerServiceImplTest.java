package com.system.trailer.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

import com.system.trailer.entity.Trailer;
import com.system.trailer.entity.TrailerApprovalHistory;
import com.system.trailer.repository.TrailerApprovalHistoryRepository;
import com.system.trailer.repository.TrailerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrailerServiceImplTest {

    @InjectMocks
    private TrailerServiceImpl trailerService;

    @Mock
    private TrailerRepository trailerRepository;

    @Mock
    private TrailerApprovalHistoryRepository historyRepository;

    private Trailer trailer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        trailer = new Trailer();
        trailer.setId(1L);
        trailer.setSerialNumber("TR12345");
        trailer.setInspected(false);
        trailer.setApproved(false);
    }

    // ----------------------------------------------------------------------
    // registerTrailer()
    // ----------------------------------------------------------------------
    @Test
    void testRegisterTrailer() {
        when(trailerRepository.save(trailer)).thenReturn(trailer);

        Trailer result = trailerService.registerTrailer(trailer);

        assertNotNull(result);
        assertEquals("TR12345", result.getSerialNumber());
        verify(trailerRepository, times(1)).save(trailer);
    }

    // ----------------------------------------------------------------------
    // getTrailerById()
    // ----------------------------------------------------------------------
    @Test
    void testGetTrailerById() {
        when(trailerRepository.findById(1L)).thenReturn(Optional.of(trailer));

        Optional<Trailer> result = trailerService.getTrailerById(1L);

        assertTrue(result.isPresent());
        assertEquals("TR12345", result.get().getSerialNumber());
        verify(trailerRepository, times(1)).findById(1L);
    }

    // ----------------------------------------------------------------------
    // updateInspectionStatus()
    // ----------------------------------------------------------------------
    @Test
    void testUpdateInspectionStatus() {
        when(trailerRepository.findById(1L)).thenReturn(Optional.of(trailer));
        when(trailerRepository.save(any(Trailer.class))).thenReturn(trailer);

        Trailer result = trailerService.updateInspectionStatus(1L, true);

        assertTrue(result.isInspected());
        verify(trailerRepository).findById(1L);
        verify(trailerRepository).save(trailer);
    }

    @Test
    void testUpdateInspectionStatus_TrailerNotFound() {
        when(trailerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> trailerService.updateInspectionStatus(1L, true));

        assertEquals("Trailer not found", ex.getMessage());
    }

    // ----------------------------------------------------------------------
    // approveTrailer()
    // ----------------------------------------------------------------------
    @Test
    void testApproveTrailer() {
        when(trailerRepository.findById(1L)).thenReturn(Optional.of(trailer));
        when(trailerRepository.save(any(Trailer.class))).thenReturn(trailer);

        Trailer result = trailerService.approveTrailer(1L, "admin");

        assertTrue(result.isApproved());
        verify(trailerRepository).save(trailer);
        verify(historyRepository, times(1)).save(any(TrailerApprovalHistory.class));
    }

    @Test
    void testApproveTrailer_TrailerNotFound() {
        when(trailerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> trailerService.approveTrailer(1L, "admin"));

        assertEquals("Trailer not found", ex.getMessage());
    }

    // ----------------------------------------------------------------------
    // getApprovalHistory()
    // ----------------------------------------------------------------------
    @Test
    void testGetApprovalHistory() {
        when(trailerRepository.findAll()).thenReturn(List.of(trailer));

        List<Trailer> result = trailerService.getApprovalHistory();

        assertEquals(1, result.size());
        verify(trailerRepository).findAll();
    }
}
