package com.example.touristguide.integration;

import com.example.touristguide.model.TouristAttraction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TouristIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCRUDOperationer() throws Exception {
        // 1. Hent alle attraktioner
        mockMvc.perform(get("/attraktioner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].navn", is("Tivoli")))
                .andExpect(jsonPath("$[1].navn", is("Den Lille Havfrue")));

        // 2. Opret en ny attraktion
        TouristAttraction nyAttraktion = new TouristAttraction("Rundetårn", "København", "Historisk tårn i København");
        nyAttraktion.setBedømmelse(BigDecimal.valueOf(4.3));

        MvcResult createResult = mockMvc.perform(post("/attraktioner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nyAttraktion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.navn", is("Rundetårn")))
                .andExpect(jsonPath("$.lokation", is("København")))
                .andReturn();
        
        // 3. Hent den oprettede attraktion
        mockMvc.perform(get("/attraktioner/Rundetårn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.navn", is("Rundetårn")))
                .andExpect(jsonPath("$.beskrivelse", is("Historisk tårn i København")));

        // 4. Opdater attraktionen
        TouristAttraction opdateretRundetårn = new TouristAttraction("Rundetårn", "København", "Opdateret beskrivelse af Rundetårn");
        opdateretRundetårn.setBedømmelse(BigDecimal.valueOf(4.5));

        mockMvc.perform(put("/attraktioner/Rundetårn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opdateretRundetårn)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beskrivelse", is("Opdateret beskrivelse af Rundetårn")));

        // 5. Verificer opdatering
        mockMvc.perform(get("/attraktioner/Rundetårn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beskrivelse", is("Opdateret beskrivelse af Rundetårn")));

        // 6. Slet attraktionen
        mockMvc.perform(delete("/attraktioner/Rundetårn"))
                .andExpect(status().isOk());

        // 7. Verificer at den er slettet
        mockMvc.perform(get("/attraktioner/Rundetårn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
} 