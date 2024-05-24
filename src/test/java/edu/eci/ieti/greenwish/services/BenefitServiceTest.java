package edu.eci.ieti.greenwish.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.eci.ieti.greenwish.exceptions.BenefitNotFoundException;
import edu.eci.ieti.greenwish.models.domain.Benefit;
import edu.eci.ieti.greenwish.models.dto.BenefitDto;
import edu.eci.ieti.greenwish.repositories.BenefitRepository;

@ExtendWith(MockitoExtension.class)
class BenefitServiceTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private BenefitService benefitService;

    private List<Benefit> benefits;
    private Benefit benefit;

    @BeforeEach
    void setup() {
        benefit = new Benefit(null, "Carulla", null, 10000, null);
        Benefit benefit2 = new Benefit(null, "Falabella", null, 20000, null);
        benefits = List.of(benefit, benefit2);
    }

    @Test
    void testFindAllBenefits() {
        when(benefitRepository.findAll()).thenReturn(benefits);
        List<Benefit> allBenefits = benefitService.findAll();
        assertNotNull(allBenefits);
        assertEquals(2, allBenefits.size());
        assertEquals(benefits, allBenefits);
        verify(benefitRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingBenefit() throws BenefitNotFoundException {
        String id = "1";
        when(benefitRepository.findById(id)).thenReturn(Optional.of(benefit));
        Benefit foundBenefit = benefitService.findById(id);
        verify(benefitRepository, times(1)).findById(id);
        assertEquals(benefit, foundBenefit);
    }

    @Test
    void testFindByIdNotFound() {
        String id = "1";
        when(benefitRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(BenefitNotFoundException.class, () -> benefitService.findById(id));
        verify(benefitRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNewBenefit() {
        BenefitDto benefitDto = new BenefitDto("Carulla", 10000);
        when(benefitRepository.save(benefit)).thenReturn(benefit);
        Benefit savedBenefit = benefitService.save(benefitDto);
        assertNotNull(savedBenefit);
        verify(benefitRepository, times(1)).save(benefit);
        assertEquals(benefit, savedBenefit);
    }

    @Test
    void testUpdateExistingBenefit() {
        String id = "1";
        BenefitDto benefitDto = new BenefitDto("Exito", 5000);
        when(benefitRepository.findById(id)).thenReturn(Optional.of(benefit));
        benefitService.update(benefitDto, id);
        verify(benefitRepository, times(1)).findById(id);
        verify(benefitRepository, times(1)).save(benefit);
        assertEquals(benefitDto.getName(), benefit.getName());
        assertEquals(benefitDto.getValue(), benefit.getValue());
    }

    @Test
    void testUpdateNotExistingBenefit() {
        String id = "1";
        BenefitDto benefitDto = new BenefitDto("Exito", 5000);
        when(benefitRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(BenefitNotFoundException.class, () -> benefitService.update(benefitDto, id));
        verify(benefitRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteExistingBenefit() {
        String id = "1";
        when(benefitRepository.existsById(id)).thenReturn(true);
        benefitService.deleteById(id);
        verify(benefitRepository, times(1)).deleteById(id);
        verify(benefitRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteNotExistingBenefit() {
        String id = "1";
        when(benefitRepository.existsById(id)).thenReturn(false);
        assertThrows(BenefitNotFoundException.class, () -> benefitService.deleteById(id));
        verify(benefitRepository, times(1)).existsById(id);
    }

}
