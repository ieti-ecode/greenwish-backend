package edu.eci.ieti.ecored.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.eci.ieti.ecored.controller.benefit.BenefitController;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.repository.document.Benefit;
import edu.eci.ieti.ecored.service.benefit.BenefitService;

@ExtendWith(MockitoExtension.class)
public class BenefitControllerTest {

    @Mock
    private BenefitService benefitService;

    @InjectMocks
    private BenefitController benefitController;

    @Test
    void getBenefitById_BenefitExists() throws BenefitNotFoundException {
        String userId = "123";
        Benefit benefit = new Benefit("Carulla", 10000);
        when(benefitService.findById(userId)).thenReturn(benefit);

        ResponseEntity<Benefit> response = benefitController.findById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Carulla", response.getBody().getName());
    }

}
