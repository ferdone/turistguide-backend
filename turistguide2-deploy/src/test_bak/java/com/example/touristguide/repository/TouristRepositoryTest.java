package com.example.touristguide.repository;

import com.example.touristguide.model.TouristAttraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TouristRepositoryTest {

    @Autowired
    private TouristRepository repository;

    private TouristAttraction testSeværdighed;

    @BeforeEach
    void setUp() {
        testSeværdighed = new TouristAttraction("Test Seværdighed", "Test Lokation", "Test Beskrivelse");
        testSeværdighed.setBedømmelse(BigDecimal.valueOf(4.5));
    }

    @Test
    void testFindAll() {
        List<TouristAttraction> seværdigheder = repository.findAll();
        assertFalse(seværdigheder.isEmpty());
    }

    @Test
    void testSaveAndFindById() {
        TouristAttraction savedSeværdighed = repository.save(testSeværdighed);
        assertNotNull(savedSeværdighed.getId());

        Optional<TouristAttraction> foundSeværdighed = repository.findById(savedSeværdighed.getId());
        assertTrue(foundSeværdighed.isPresent());
        assertEquals("Test Seværdighed", foundSeværdighed.get().getNavn());
    }

    @Test
    void testFindByNavn() {
        repository.save(testSeværdighed);
        Optional<TouristAttraction> foundSeværdighed = repository.findByNavn("Test Seværdighed");
        assertTrue(foundSeværdighed.isPresent());
        assertEquals("Test Lokation", foundSeværdighed.get().getLokation());
    }

    @Test
    void testUpdate() {
        TouristAttraction savedSeværdighed = repository.save(testSeværdighed);
        savedSeværdighed.setBeskrivelse("Opdateret beskrivelse");
        
        TouristAttraction updatedSeværdighed = repository.save(savedSeværdighed);
        
        Optional<TouristAttraction> foundSeværdighed = repository.findById(updatedSeværdighed.getId());
        assertTrue(foundSeværdighed.isPresent());
        assertEquals("Opdateret beskrivelse", foundSeværdighed.get().getBeskrivelse());
    }

    @Test
    void testDeleteByNavn() {
        repository.save(testSeværdighed);
        repository.deleteByNavn("Test Seværdighed");
        
        Optional<TouristAttraction> foundSeværdighed = repository.findByNavn("Test Seværdighed");
        assertFalse(foundSeværdighed.isPresent());
    }
} 