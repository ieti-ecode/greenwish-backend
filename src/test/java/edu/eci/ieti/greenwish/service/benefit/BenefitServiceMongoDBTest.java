package edu.eci.ieti.greenwish.service.benefit;

import edu.eci.ieti.greenwish.controller.benefit.BenefitDto;
import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.repository.BenefitRepository;
import edu.eci.ieti.greenwish.repository.document.Benefit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BenefitServiceMongoDBTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private BenefitServiceMongoDB benefitServiceMongoDB;

    @Test
    void testFindAllBenefits() {
        List<Benefit> benefits = Arrays.asList(
                new Benefit("Exito", 5000),
                new Benefit("Carulla", 10000));
        Mockito.when(benefitRepository.findAll()).thenReturn(benefits);
        List<Benefit> allBenefits = benefitServiceMongoDB.all();
        Assertions.assertNotNull(allBenefits);
        assertEquals(2, allBenefits.size());
        assertEquals(benefits, allBenefits);
        verify(benefitRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingBenefit() throws BenefitNotFoundException {
        String id = "1";
        BenefitDto benefitDto = new BenefitDto("Exito", 5000);
        Benefit benefit = new Benefit(benefitDto);
        when(benefitRepository.findById(id)).thenReturn(Optional.of(benefit));
        Benefit foundBenefit = benefitServiceMongoDB.findById(id);
        verify(benefitRepository, times(1)).findById(id);
        assertEquals(benefit, foundBenefit);
    }

    @Test
    void testFindByIdNotFound() {
        String id = "1";
        when(benefitRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(BenefitNotFoundException.class, () -> benefitServiceMongoDB.findById(id));
        verify(benefitRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNewBenefit() {
        BenefitDto benefitDto = new BenefitDto("Exito", 5000);
        Benefit benefit = new Benefit(benefitDto);
        Mockito.when(benefitRepository.save(benefit)).thenReturn(benefit);
        Benefit savedBenefit = benefitServiceMongoDB.save(benefitDto);
        Assertions.assertNotNull(savedBenefit);
        verify(benefitRepository, times(1)).save(benefit);
        assertEquals(benefit, savedBenefit);
    }

    @Test
    void testUpdateExistingBenefit() {
        String id = "1";
        BenefitDto benefitDto = new BenefitDto("Exito", 5000);
        Benefit benefit = new Benefit("Carulla", 10000);
        when(benefitRepository.findById(id)).thenReturn(Optional.of(benefit));
        benefitServiceMongoDB.update(benefitDto, id);
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
        assertThrows(BenefitNotFoundException.class, () -> benefitServiceMongoDB.update(benefitDto, id));
        verify(benefitRepository, times(1)).findById(id);
    }


    @Test
    void testDeleteExistingBenefit() {
        String id = "1";
        when(benefitRepository.existsById(id)).thenReturn(true);
        benefitServiceMongoDB.deleteById(id);
        verify(benefitRepository, times(1)).deleteById(id);
        verify(benefitRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteNotExistingBenefit() {
        String id = "1";
        when(benefitRepository.existsById(id)).thenReturn(false);
        assertThrows(BenefitNotFoundException.class, () -> benefitServiceMongoDB.deleteById(id));
        verify(benefitRepository, times(1)).existsById(id);
    }
}
