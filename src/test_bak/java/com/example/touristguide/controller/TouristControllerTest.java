package com.example.touristguide.controller;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.TouristService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
public class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TouristService touristService;

    private TouristAttraction tivoli;
    private TouristAttraction legoland;

    @BeforeEach
    void setUp() {
        tivoli = new TouristAttraction("Tivoli", "København", "Historisk forlystelsespark");
        tivoli.setId(1L);
        tivoli.setBedømmelse(BigDecimal.valueOf(4.5));

        legoland = new TouristAttraction("Legoland", "Billund", "LEGO temapark");
        legoland.setId(2L);
        legoland.setBedømmelse(BigDecimal.valueOf(4.7));
    }

    @Test
    void testGetAllAttractions() throws Exception {
        when(touristService.findAll()).thenReturn(Arrays.asList(tivoli, legoland));

        mockMvc.perform(get("/attraktioner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].navn", is("Tivoli")))
                .andExpect(jsonPath("$[1].navn", is("Legoland")));

        verify(touristService, times(1)).findAll();
    }

    @Test
    void testGetAttractionByName_Found() throws Exception {
        when(touristService.findByNavn("Tivoli")).thenReturn(Optional.of(tivoli));

        mockMvc.perform(get("/attraktioner/Tivoli"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.navn", is("Tivoli")))
                .andExpect(jsonPath("$.lokation", is("København")));

        verify(touristService, times(1)).findByNavn("Tivoli");
    }

    @Test
    void testGetAttractionByName_NotFound() throws Exception {
        when(touristService.findByNavn("Findes Ikke")).thenReturn(Optional.empty());

        mockMvc.perform(get("/attraktioner/Findes Ikke"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(touristService, times(1)).findByNavn("Findes Ikke");
    }

    @Test
    void testAddAttraction() throws Exception {
        when(touristService.addAttraktion(any(TouristAttraction.class))).thenReturn(tivoli);

        mockMvc.perform(post("/attraktioner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tivoli)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.navn", is("Tivoli")))
                .andExpect(jsonPath("$.lokation", is("København")));

        verify(touristService, times(1)).addAttraktion(any(TouristAttraction.class));
    }

    @Test
    void testUpdateAttraction_Found() throws Exception {
        TouristAttraction opdateretTivoli = new TouristAttraction("Tivoli Gardens", "København", "Opdateret beskrivelse");
        opdateretTivoli.setId(1L);
        
        when(touristService.updateAttraktion(eq("Tivoli"), any(TouristAttraction.class))).thenReturn(opdateretTivoli);

        mockMvc.perform(put("/attraktioner/Tivoli")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opdateretTivoli)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.navn", is("Tivoli Gardens")))
                .andExpect(jsonPath("$.beskrivelse", is("Opdateret beskrivelse")));

        verify(touristService, times(1)).updateAttraktion(eq("Tivoli"), any(TouristAttraction.class));
    }

    @Test
    void testUpdateAttraction_NotFound() throws Exception {
        when(touristService.updateAttraktion(eq("Findes Ikke"), any(TouristAttraction.class))).thenReturn(null);

        mockMvc.perform(put("/attraktioner/Findes Ikke")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new TouristAttraction("Test", "Test", "Test"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(touristService, times(1)).updateAttraktion(eq("Findes Ikke"), any(TouristAttraction.class));
    }

    @Test
    void testDeleteAttraction() throws Exception {
        doNothing().when(touristService).deleteAttraktion("Tivoli");

        mockMvc.perform(delete("/attraktioner/Tivoli"))
                .andExpect(status().isOk());

        verify(touristService, times(1)).deleteAttraktion("Tivoli");
    }
} 