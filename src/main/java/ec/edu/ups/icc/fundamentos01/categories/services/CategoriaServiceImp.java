package ec.edu.ups.icc.fundamentos01.categories.services;


import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDTO;
import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;


@Service
public class CategoriaServiceImp implements CategoriaService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoriaServiceImp(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CategoriaResponseDTO create(CreateCategoriaDTO categoria) {

        if (categoryRepository.existsByName(categoria.name)) {
            throw new ConflictException("La categoría ya existe: " + categoria.name);
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setName(categoria.name);
        entity.setDescription(categoria.description);

        CategoryEntity saved = categoryRepository.save(entity);


        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.id = saved.getId();
        dto.name = saved.getName();
        dto.description = saved.getDescription();

        return dto;
    }
    // PARTE C
    // @Override
    // public long countProducts(Long categoryId) {

    //     categoryRepository.findById(categoryId)
    //             .orElseThrow(() -> new NotFoundException("Categoría no existe"));

    //     return productRepository.countProductsByCategory(categoryId);
    // }
}
