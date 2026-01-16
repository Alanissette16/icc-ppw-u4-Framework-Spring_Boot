package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;
import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.models.Product;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    public ProductServiceImpl(ProductRepository productRepo, UserRepository userRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    // ===================== MANY-TO-ONE (1:N) - COMENTADO =====================
    // @Override
    // public ProductResponseDto create(CreateProductDto dto) {
    //     UserEntity owner = userRepo.findById(dto.userId)
    //             .orElseThrow(() -> new NotFoundException("Usuario no existe"));
    //     CategoryEntity categoria = categoryRepo.findById(dto.categoryId)
    //             .orElseThrow(() -> new NotFoundException("Categoria no existe"));
    //     Product newProduct = Product.fromDto(dto);
    //     ProductEntity entity = newProduct.toEntity(owner, categoria);
    //     ProductEntity saved = productRepo.save(entity);
    //     return toResponseDTO(saved);
    // }

    // ===================== MANY-TO-MANY (N:N) =====================
    @Override
    public ProductResponseDto create(CreateProductDto dto) {

        // 1. Validar usuario
        UserEntity owner = userRepo.findById(dto.userId)
                .orElseThrow(() -> new NotFoundException("Usuario no existe"));

        // 2. Validar y cargar categorías (N:N)
        Set<CategoryEntity> categories = new HashSet<>();

        for (Long categoryId : dto.categoryIds) {
            CategoryEntity category = categoryRepo.findById(categoryId)
                    .orElseThrow(() ->
                            new NotFoundException("Categoria no existe con ID: " + categoryId));
            categories.add(category);
        }
        // 3. DTO → Dominio
        Product newProduct = Product.fromDto(dto);
        // 4. Dominio → Entidad (con SET de categorías)
        ProductEntity entity = newProduct.toEntity(owner, categories);
        // 5. Persistir
        ProductEntity saved = productRepo.save(entity);
        // 6. Respuesta
        return toResponseDTO(saved);
    }

    private ProductResponseDto toResponseDTO(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();

        // Usuario (N:1)
        ProductResponseDto.UserSummaryDTO ownerDTO =
                new ProductResponseDto.UserSummaryDTO();
        ownerDTO.id = entity.getOwner().getId();
        ownerDTO.username = entity.getOwner().getName();
        dto.user = ownerDTO;

        // ===================== MANY-TO-ONE (1:N) - COMENTADO =====================
        // CategoriaResponseDTO categoryDTO = new CategoriaResponseDTO();
        // categoryDTO.id = entity.getCategory().getId();
        // categoryDTO.name = entity.getCategory().getName();
        // dto.category = categoryDTO;

        // ===================== MANY-TO-MANY (N:N) =====================
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

        return dto;
    }


    @Override
    public List<ProductResponseDto> findAll() {
        return productRepo.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponseDto findOne(int id) {
        return productRepo.findById((long) id)
                .map(ProductMapper::toResponse)
                .orElseThrow(() ->
                        new NotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductResponseDto update(int id, UpdateProductDto dto) {
        ProductEntity product = productRepo.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.name);
        product.setDescription(dto.description);
        product.setPrice(dto.price);
        product.setStock(dto.stock);

        return ProductMapper.toResponse(productRepo.save(product));
    }

    @Override
    public ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto) {
        ProductEntity product = productRepo.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.name != null) product.setName(dto.name);
        if (dto.description != null) product.setDescription(dto.description);
        if (dto.price != null) product.setPrice(dto.price);
        if (dto.stock != null) product.setStock(dto.stock);

        return ProductMapper.toResponse(productRepo.save(product));
    }

    @Override
    public void delete(int id) {
        if (!productRepo.existsById((long) id)) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById((long) id);
    }

    @Override
    public ProductResponseDto validateName(int id, String name) {
        Optional<ProductEntity> existingProduct = productRepo.findByName(name);

        if (existingProduct.isPresent()
                && !existingProduct.get().getId().equals((long) id)) {
            throw new ConflictException(
                    "El nombre del producto ya está en uso: " + name);
        }
        return null;
    }

    @Override
    public List<ProductResponseDto> findByUser(Long userId) {
        return productRepo.findByOwnerId(userId)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductResponseDto> findByCategory(Long categoryId) {
        return productRepo.findByCategoryIdAndPriceGreaterThan(categoryId, 0.0)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}
