package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repository.CategoryRepository;
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



    public ProductServiceImpl(ProductRepository productRepo,UserRepository userRepo,
            CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        // CON UN USAURIO Y CON CATEGORIA
        // VALIDA SUS EXISTENCIA
        UserEntity owner = userRepo.findById(dto.userId)
                .orElseThrow(() -> new NotFoundException("Usuario no existe"));
        CategoryEntity categoria = categoryRepo.findById(dto.categoryId)
                .orElseThrow(() -> new NotFoundException("Categoria no existe"));
        // Convierte DTO -> Domain
        Product newProduct = Product.fromDto(dto);
        ProductEntity entity = newProduct.toEntity(owner,categoria);
        
        // PERSISTIR
        ProductEntity saved = productRepo.save(entity);

        return toResponseDTO(saved);
    }

    private ProductResponseDto toResponseDTO(ProductEntity entity){
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();
        
        ProductResponseDto.UserSummaryDTO ownerDTO = new ProductResponseDto.UserSummaryDTO();
        ownerDTO.id = entity.getOwner().getId();
        ownerDTO.username = entity.getOwner().getName();

        CategoriaResponseDTO categoryDTO = new CategoriaResponseDTO();
        categoryDTO.id = entity.getCategory().getId();
        categoryDTO.name =  entity.getCategory().getName();

        dto.user = ownerDTO;
        dto.category = categoryDTO;
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
        .orElseThrow(() -> new NotFoundException("Producto no encontrado")
        );
    }

    // @Override
    // public ProductResponseDto create(CreateProductDto dto) {

    //     if (productRepo.existsByName(dto.name)) {
    //     throw new ConflictException(
    //         "Ya existe un producto con el nombre: " + dto.name
    //     );
    // }

    // ProductEntity entity = ProductMapper.toEntity(dto);
    // ProductEntity saved = productRepo.save(entity);

    // return ProductMapper.toResponse(saved);
    // }

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
        ProductEntity existingProduct = productRepo.findByName(name);
        if (existingProduct != null && existingProduct.getId() != (long) id) {
            throw new ConflictException("El nombre del producto ya está en uso: " + name);
        }
        return null;
    }
}
