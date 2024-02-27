package edu.eci.ieti.ecored.service;

import edu.eci.ieti.ecored.Service.BenefitServiceMongoDB;
import edu.eci.ieti.ecored.controller.benefit.BenefitDto;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.repository.BenefitRepository;
import edu.eci.ieti.ecored.repository.document.Benefit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
//@TestPropertySource(properties = {"spring.data.mongodb.uri=mongodb://localhost/testdb"})
public class BenefitServiceMongoDBTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private BenefitServiceMongoDB benefitServiceMongoDB;

    @Test
    @Order(1)
    public void testFindAllBenefits(){
        List<Benefit> benefitsListMock = Arrays.asList(
                new Benefit("Exito", 5000),
                new Benefit("Carulla", 10000)
        );
        Mockito.when(benefitRepository.findAll()).thenReturn(benefitsListMock);
        List<Benefit> benefits = benefitServiceMongoDB.all();
        Assertions.assertNotNull(benefits);
        Assertions.assertTrue(benefits.size() > 0);
        Assertions.assertEquals(10000, benefits.get(1).getValue());
    }

    @Test
    @Order(2)
    public void testFindBenefitById() throws BenefitNotFoundException {
        Optional<Benefit> benefitMock = Optional.of(new Benefit("Exito", 5000));
        Mockito.when(benefitRepository.findById("1")).thenReturn(benefitMock);
        Benefit benefit = benefitServiceMongoDB.findById("1");
        Assertions.assertNotNull(benefit);
        Assertions.assertEquals("Exito", benefit.getName());
    }

    @Test
    @Order(3)
    public void testCreateBenefit() {
        BenefitDto benefitFromController = new BenefitDto("Exito", 5000);
        Benefit benefitMock = new Benefit(benefitFromController.getName(), benefitFromController.getValue());
        Mockito.when(benefitRepository.save(benefitMock)).thenReturn(benefitMock);
        Benefit benefitSaved = benefitRepository.save(benefitMock);
        Assertions.assertNotNull(benefitSaved);
        Assertions.assertEquals("Exito", benefitSaved.getName());
    }
}