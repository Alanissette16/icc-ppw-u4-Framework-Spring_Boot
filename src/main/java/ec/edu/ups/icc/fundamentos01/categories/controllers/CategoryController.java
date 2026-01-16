package ec.edu.ups.icc.fundamentos01.categories.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDTO;
import ec.edu.ups.icc.fundamentos01.categories.services.CategoriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoriaService service;


    public CategoryController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> create(@Valid @RequestBody CreateCategoriaDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // PARTE C
    // @GetMapping("/{id}/products/count")
    // public ResponseEntity<Long> countProducts(@PathVariable Long id) {
    //     return ResponseEntity.ok(service.countProducts(id));
    // }
    
}
