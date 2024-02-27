package edu.eci.ieti.ecored.controller.benefit;

import edu.eci.ieti.ecored.Service.BenefitService;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.repository.document.Benefit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/v1/benefit")
public class BenefitController {

    private final BenefitService benefitService;

    public BenefitController(@Autowired BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    @GetMapping
    public List<Benefit> all(){
        return benefitService.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Benefit> findById(@PathVariable String id)  {
        try{
            return ResponseEntity.ok(benefitService.findById(id));
        }catch (BenefitNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Benefit> create(@RequestBody BenefitDto benefitDto){

        return ResponseEntity.ok(benefitService.create(benefitDto));
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<Benefit> update( @RequestBody BenefitDto benefitDto, @PathVariable String id )
    {
        return ResponseEntity.ok( benefitService.update( benefitDto, id ) );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Boolean> delete( @PathVariable String id )
    {
        return ResponseEntity.ok( benefitService.deleteById( id ) );
    }

}
