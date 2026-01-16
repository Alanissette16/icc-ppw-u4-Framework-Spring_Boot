package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

public class ProductMapper {

    public static ProductEntity toEntity(CreateProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.name);
        entity.setDescription(dto.description);
        entity.setPrice(dto.price);
        entity.setStock(dto.stock);
        return entity;
    }

    public static ProductResponseDto toResponse(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.description = entity.getDescription();
        dto.price = entity.getPrice();
        dto.stock = entity.getStock();
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();

        if (entity.getOwner() != null) {
            ProductResponseDto.UserSummaryDTO userDto = new ProductResponseDto.UserSummaryDTO();
            userDto.id = entity.getOwner().getId();
            userDto.username = entity.getOwner().getName();
            userDto.email = entity.getOwner().getEmail();
            dto.user = userDto;
        }

        // ===================== MANY-TO-ONE (1:N) - COMENTADO =====================
        // if (entity.getCategory() != null) {
        //     CategoriaResponseDTO catDto = new CategoriaResponseDTO();
        //     catDto.id = entity.getCategory().getId();
        //     catDto.name = entity.getCategory().getName();
        //     catDto.description = entity.getCategory().getDescription();
        //     dto.category = catDto;
        // }
        
        // ===================== MANY-TO-MANY (N:N) =====================
        if (entity.getCategories() != null) {
            dto.categories = entity.getCategories()
                    .stream()
                    .map(cat -> {
                        CategoriaResponseDTO c = new CategoriaResponseDTO();
                        c.id = cat.getId();
                        c.name = cat.getName();
                        c.description = cat.getDescription();
                        return c;
                    })
                    .toList();
        }

        return dto;
    }
}
