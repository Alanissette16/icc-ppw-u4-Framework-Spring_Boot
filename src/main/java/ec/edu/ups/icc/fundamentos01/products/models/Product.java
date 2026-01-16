package ec.edu.ups.icc.fundamentos01.products.models;

import java.util.Set;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class Product {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private String createdAt;

    public Product(Long id, String name, double price, int stock) {

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

    }

    // Sin ID
    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // ---------- FACTORIES ----------

    public static Product fromDto(CreateProductDto dto) {
        return new Product(dto.name, dto.price, dto.stock);
    }

    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock()

        );
    }

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        entity.setName(name);
        entity.setPrice(price);
        entity.setStock(stock);

        return entity;
    }

    // Parte A************
    // public ProductEntity toEntity(UserEntity owner, CategoryEntity
    // categoryEntity) {
    // ProductEntity entity = new ProductEntity();
    // entity.setName(name);
    // entity.setPrice(price);
    // entity.setStock(stock);

    // entity.setOwner(owner);
    // entity.setCategory(categoryEntity);

    // return entity;
    // }

    // Parte B************
    public ProductEntity toEntity(UserEntity owner, Set<CategoryEntity> categories) {
        ProductEntity entity = new ProductEntity();

        entity.setName(name);
        entity.setPrice(price);
        entity.setStock(stock);
        entity.setOwner(owner);
        entity.setCategories(categories);

        return entity;
    }

    public ProductResponseDto toResponseDto() {
        ProductResponseDto dto = new ProductResponseDto();
        dto.name = this.name;
        dto.price = this.price;
        dto.stock = this.stock;
        return dto;
    }

    // ---------- UPDATES ----------
    public void update(UpdateProductDto dto) {
        this.name = dto.name;
        this.price = dto.price;
        this.stock = dto.stock;
    }

    public void partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null)
            this.name = dto.name;
        if (dto.price != null)
            this.price = dto.price;
        if (dto.stock != null)
            this.stock = dto.stock;
    }

}
