package edu.eci.ieti.greenwish.controller.company;

import java.net.URI;
import java.util.List;

import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Company;
import edu.eci.ieti.greenwish.service.company.CompanyService;

@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(@Autowired CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> all() {
        return ResponseEntity.ok(companyService.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable String id) throws CompanyNotFoundException {
        try {
            return ResponseEntity.ok(companyService.findById(id));
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Company> create(@RequestBody CompanyDto companyDto) {
        Company savedCompany = companyService.save(companyDto);
        URI location = URI.create(String.format("/v1/company/%s", savedCompany.getId()));
        return ResponseEntity.created(location).body(savedCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody CompanyDto companyDto, @PathVariable String id) {
        try {
            companyService.update(companyDto, id);
            return ResponseEntity.ok().build();
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        try {
            companyService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
