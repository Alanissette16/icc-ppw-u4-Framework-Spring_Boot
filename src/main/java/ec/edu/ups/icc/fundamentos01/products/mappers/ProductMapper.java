package ec.edu.ups.icc.fundamentos01.products.mappers;

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

    public static ProductResponseDto toResponse (ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.name = entity.getName();
        dto.description = entity.getDescription();
        dto.price = entity.getPrice();
        dto.stock = entity.getStock();
        return dto;
    }
}
