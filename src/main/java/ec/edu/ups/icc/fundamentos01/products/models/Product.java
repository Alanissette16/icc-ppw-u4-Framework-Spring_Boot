package ec.edu.ups.icc.fundamentos01.products.models;

import java.time.LocalDateTime;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private LocalDateTime createdAt;

    public Product(int id, String name, double price, int stock, LocalDateTime createdAt) {

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nombre inválido");

        if (price < 0)
            throw new IllegalArgumentException("Precio inválido");

        if (stock < 0)
            throw new IllegalArgumentException("Stock inválido");

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = LocalDateTime.now();
    }

    // ---------- FACTORIES ----------
    public static Product fromDto(CreateProductDto dto) {
        return new Product(0, dto.name, dto.price, dto.stock, LocalDateTime.now());
    }

    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId().intValue(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock(),
                entity.getCreatedAt()
        );
    }

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (id > 0) entity.setId((long) id);
        entity.setName(name);
        entity.setPrice(price);
        entity.setStock(stock);

        if (this.createdAt == null) {
        entity.setCreatedAt(LocalDateTime.now());
        } else {
        entity.setCreatedAt(this.createdAt);
        }
        return entity;
    }

    public ProductResponseDto toResponseDto() {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = this.id;
        dto.name = this.name;
        dto.price = this.price;
        dto.stock = this.stock;
        dto.createdAt = this.createdAt;
        return dto;
    }

    // ---------- UPDATES ----------
    public void update(UpdateProductDto dto) {
        this.name = dto.name;
        this.price = dto.price;
        this.stock = dto.stock;
    }

    public void partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null) this.name = dto.name;
        if (dto.price != null) this.price = dto.price;
        if (dto.stock != null) this.stock = dto.stock;
    }

}
