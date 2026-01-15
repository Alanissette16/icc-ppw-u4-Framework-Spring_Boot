package ec.edu.ups.icc.fundamentos01.categories.dtos;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;

public class CategoriaResponseDTO {
    public Long id;
    public String name;
    public String description;
    
    public CategoriaResponseDTO formEntity(CategoryEntity entity){
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.description = entity.getDescription();
        return dto;
    }
}
