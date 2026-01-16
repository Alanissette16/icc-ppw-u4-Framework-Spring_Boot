package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductService service;

    public ProductsController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findOne(@PathVariable int id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @GetMapping("/user/{userId}")
    public List<ProductResponseDto> byUser(@PathVariable Long userId) {
        return service.findByUser(userId);
    }

    // @GetMapping("/category/{categoryId}")
    // public List<ProductResponseDto> byCategory(@PathVariable Long categoryId) {
    //     return service.findByCategory(categoryId);
    // }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody CreateProductDto dto) {
        ProductResponseDto created = service.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable int id, @RequestBody UpdateProductDto dto) {
        ProductResponseDto updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> partialUpdate(@PathVariable int id,
            @RequestBody PartialUpdateProductDto dto) {
        ProductResponseDto updated = service.partialUpdate(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validate-name")
    public ResponseEntity<Void> validateName(@RequestParam int id, @RequestParam String name) {
        service.validateName(id, name);
        return ResponseEntity.ok().build();
    }

}
