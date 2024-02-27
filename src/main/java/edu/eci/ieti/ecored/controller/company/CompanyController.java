package edu.eci.ieti.ecored.controller.company;

import edu.eci.ieti.ecored.Service.CompanyService;
import edu.eci.ieti.ecored.exception.CompanyNotFoundException;
import edu.eci.ieti.ecored.repository.document.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(@Autowired CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> all(){
        return companyService.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable String id) throws CompanyNotFoundException {
        try {
            return ResponseEntity.ok(companyService.findById(id));
        }catch (CompanyNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Company> create(@RequestBody CompanyDto companyDto){
        return ResponseEntity.ok(companyService.create(companyDto));
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<Company> update( @RequestBody CompanyDto companyDto, @PathVariable String id )
    {
        return ResponseEntity.ok( companyService.update( companyDto, id ) );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Boolean> delete( @PathVariable String id )
    {
        return ResponseEntity.ok( companyService.deleteById( id ) );
    }

}
