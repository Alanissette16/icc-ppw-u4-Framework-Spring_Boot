package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.models.Product;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;



@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
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

        ProductEntity product = productRepo.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {

        Product product = Product.fromDto(dto);
        ProductEntity saved = productRepo.save(product.toEntity());
        return Product.fromEntity(saved).toResponseDto();
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
}
