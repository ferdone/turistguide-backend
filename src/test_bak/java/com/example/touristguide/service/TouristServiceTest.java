package com.example.touristguide.service;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.repository.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TouristServiceTest {

    @Mock
    private TouristRepository touristRepository;

    @InjectMocks
    private TouristService touristService;

    private TouristAttraction testSeværdighed1;
    private TouristAttraction testSeværdighed2;

    @BeforeEach
    void setUp() {
        testSeværdighed1 = new TouristAttraction("Tivoli", "København", "Historisk forlystelsespark");
        testSeværdighed1.setId(1L);
        testSeværdighed1.setBedømmelse(BigDecimal.valueOf(4.5));

        testSeværdighed2 = new TouristAttraction("Legoland", "Billund", "LEGO temapark");
        testSeværdighed2.setId(2L);
        testSeværdighed2.setBedømmelse(BigDecimal.valueOf(4.7));
    }

    @Test
    void testFindAll() {
        when(touristRepository.findAll()).thenReturn(Arrays.asList(testSeværdighed1, testSeværdighed2));

        List<TouristAttraction> result = touristService.findAll();

        assertEquals(2, result.size());
        verify(touristRepository, times(1)).findAll();
    }

    @Test
    void testFindByNavn_Found() {
        when(touristRepository.findByNavn("Tivoli")).thenReturn(Optional.of(testSeværdighed1));

        Optional<TouristAttraction> result = touristService.findByNavn("Tivoli");

        assertTrue(result.isPresent());
        assertEquals("Tivoli", result.get().getNavn());
        verify(touristRepository, times(1)).findByNavn("Tivoli");
    }

    @Test
    void testFindByNavn_NotFound() {
        when(touristRepository.findByNavn("Findes Ikke")).thenReturn(Optional.empty());

        Optional<TouristAttraction> result = touristService.findByNavn("Findes Ikke");

        assertFalse(result.isPresent());
        verify(touristRepository, times(1)).findByNavn("Findes Ikke");
    }

    @Test
    void testAddAttraktion() {
        when(touristRepository.save(any(TouristAttraction.class))).thenReturn(testSeværdighed1);

        TouristAttraction result = touristService.addAttraktion(testSeværdighed1);

        assertNotNull(result);
        assertEquals("Tivoli", result.getNavn());
        verify(touristRepository, times(1)).save(testSeværdighed1);
    }

    @Test
    void testUpdateAttraktion_Found() {
        TouristAttraction opdateretSeværdighed = new TouristAttraction("Tivoli Gardens", "København", "Opdateret beskrivelse");
        
        when(touristRepository.findByNavn("Tivoli")).thenReturn(Optional.of(testSeværdighed1));
        when(touristRepository.save(any(TouristAttraction.class))).thenAnswer(i -> i.getArgument(0));

        TouristAttraction result = touristService.updateAttraktion("Tivoli", opdateretSeværdighed);

        assertNotNull(result);
        assertEquals("Tivoli Gardens", result.getNavn());
        assertEquals("Opdateret beskrivelse", result.getBeskrivelse());
        verify(touristRepository, times(1)).findByNavn("Tivoli");
        verify(touristRepository, times(1)).save(any(TouristAttraction.class));
    }

    @Test
    void testUpdateAttraktion_NotFound() {
        TouristAttraction opdateretSeværdighed = new TouristAttraction("Findes Ikke", "Ukendt", "Opdateret beskrivelse");
        
        when(touristRepository.findByNavn("Findes Ikke")).thenReturn(Optional.empty());

        TouristAttraction result = touristService.updateAttraktion("Findes Ikke", opdateretSeværdighed);

        assertNull(result);
        verify(touristRepository, times(1)).findByNavn("Findes Ikke");
        verify(touristRepository, never()).save(any(TouristAttraction.class));
    }

    @Test
    void testDeleteAttraktion() {
        doNothing().when(touristRepository).deleteByNavn("Tivoli");

        touristService.deleteAttraktion("Tivoli");

        verify(touristRepository, times(1)).deleteByNavn("Tivoli");
    }
} 