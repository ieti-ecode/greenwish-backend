package edu.eci.ieti.greenwish.controller.benefit;

import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Benefit;
import edu.eci.ieti.greenwish.service.benefit.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/benefit")
public class BenefitController {

    private final BenefitService benefitService;

    public BenefitController(@Autowired BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    @GetMapping
    public List<Benefit> all() {
        return benefitService.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Benefit> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(benefitService.findById(id));
        } catch (BenefitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Benefit> create(@RequestBody BenefitDto benefitDto) {
        Benefit savedBenefit = benefitService.save(benefitDto);
        URI location = URI.create(String.format("/v1/company/%s", savedBenefit.getId()));
        return ResponseEntity.created(location).body(savedBenefit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody BenefitDto benefitDto, @PathVariable String id) {
        try {
            benefitService.update(benefitDto, id);
            return ResponseEntity.ok().build();
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        try {
            benefitService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
