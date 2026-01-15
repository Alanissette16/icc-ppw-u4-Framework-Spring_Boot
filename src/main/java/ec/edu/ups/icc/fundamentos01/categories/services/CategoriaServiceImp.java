package ec.edu.ups.icc.fundamentos01.categories.services;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDTO;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repository.CategoryRepository;

public class CategoriaServiceImp  implements CategoriaService {

    private CategoryRepository categoryRepository;

    public CategoriaServiceImp(CategoryRepository repo) {
        this.categoryRepository = repo;
    }

    @Override
    public CategoriaResponseDTO save(CreateCategoriaDTO categoria){
        CategoryEntity entity = new CategoryEntity();
        entity.setName(categoria.name);
        entity.setDescription(categoria.description);
        categoryRepository.save(entity);
        //crear categoria responsedto = dto
        return new CategoriaResponseDTO().formEntity(entity);   

    }
    
}
