package ec.edu.ups.icc.fundamentos01.categories.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.Fundamentos01Application;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDTO;
import ec.edu.ups.icc.fundamentos01.categories.services.CategoriaService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoriaService service;
    private final Fundamentos01Application fundamentos01Application;

    public CategoryController(CategoriaService service, Fundamentos01Application fundamentos01Application) {
        this.service = service;
        this.fundamentos01Application = fundamentos01Application;
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody CreateCategoriaDTO dto) {
        service.save(dto);
        return ResponseEntity.ok("Categoria creada");
    }
    


    
}
