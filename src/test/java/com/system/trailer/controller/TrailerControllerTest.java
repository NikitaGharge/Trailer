package com.system.trailer.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.trailer.dto.TrailerRequestDTO;
import com.system.trailer.entity.Trailer;
import com.system.trailer.entity.TrailerApprovalHistory;
import com.system.trailer.mapper.TrailerMapper;
import com.system.trailer.service.TrailerApprovalHistoryService;
import com.system.trailer.service.TrailerService;
import com.system.trailer.util.JwtFilter;
import com.system.trailer.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(TrailerController.class)
@AutoConfigureMockMvc(addFilters = false)
class TrailerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrailerService trailerService;

    @MockBean
    private TrailerApprovalHistoryService historyService;

    @Autowired
    private ObjectMapper objectMapper;

    private Trailer trailer;
    
    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtUtil jwtUtil;
    
    @MockBean
    private TrailerMapper trailerMapper;

    @BeforeEach
    void setup() {
        trailer = new Trailer();
        trailer.setId(1L);
        trailer.setSerialNumber("TR12345");
        trailer.setInspected(false);
        trailer.setApproved(false);
    }

    // ------------------------------------------------------------------
    // POST /trailers - registerTrailer
    // ------------------------------------------------------------------
    @Test
    void testRegisterTrailer() throws Exception {   TrailerRequestDTO requestDTO = new TrailerRequestDTO();
    requestDTO.setSerialNumber("TR12345");

 // Mock the static mapper
    try (MockedStatic<com.system.trailer.mapper.TrailerMapper> mockedMapper = mockStatic(com.system.trailer.mapper.TrailerMapper.class)) {
        mockedMapper.when(() -> com.system.trailer.mapper.TrailerMapper.toEntity(any(TrailerRequestDTO.class)))
                    .thenReturn(trailer);

        when(trailerService.registerTrailer(any(Trailer.class))).thenReturn(trailer);

        mockMvc.perform(post("/trailers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()) 
                .andExpect(jsonPath("$.serialNumber").value("TR12345"));
    }
    }

    // ------------------------------------------------------------------
    // GET /trailers/{id} - trailer found
    // ------------------------------------------------------------------
    @Test
    void testGetTrailer_Found() throws Exception {
        when(trailerService.getTrailerById(1L)).thenReturn(Optional.of(trailer));

        mockMvc.perform(get("/trailers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNumber").value("TR12345"));
    }

    // ------------------------------------------------------------------
    // GET /trailers/{id} - not found
    // ------------------------------------------------------------------
    @Test
    void testGetTrailer_NotFound() throws Exception {
        when(trailerService.getTrailerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/trailers/1"))
                .andExpect(status().isNotFound());
    }

    // ------------------------------------------------------------------
    // PUT /trailers/{id}/inspection
    // ------------------------------------------------------------------
    @Test
    void testUpdateInspectionStatus() throws Exception {
        trailer.setInspected(true);

        when(trailerService.updateInspectionStatus(1L, true)).thenReturn(trailer);

        mockMvc.perform(put("/trailers/1/inspection")
                        .param("inspected", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inspected").value(true));
    }

    // ------------------------------------------------------------------
    // PUT /trailers/{id}/{approvedBy}/approval
    // ------------------------------------------------------------------
    @Test
    void testApproveTrailer() throws Exception {
        trailer.setApproved(true);

        when(trailerService.approveTrailer(1L, "admin")).thenReturn(trailer);

        mockMvc.perform(put("/trailers/1/admin/approval"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approved").value(true));
    }

    // ------------------------------------------------------------------
    // GET /trailers/history
    // ------------------------------------------------------------------
    @Test
    void testGetHistory() throws Exception {
        TrailerApprovalHistory h = new TrailerApprovalHistory();
        h.setApproved(true);

        when(historyService.getHistory("TR12345", true, "approvalDate", "DESC"))
                .thenReturn(List.of(h));

        mockMvc.perform(get("/trailers/history")
                        .param("serialNumber", "TR12345")
                        .param("approved", "true")
                        .param("sortBy", "approvalDate")
                        .param("direction", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].approved").value(true));
    }
}
