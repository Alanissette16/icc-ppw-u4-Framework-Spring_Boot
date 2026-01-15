package ec.edu.ups.icc.fundamentos01.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    
    boolean existsByName(String name);
    ProductEntity findByName(String name);
    //Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findByOwnerId(Long userId);
    List<ProductEntity> findByCategoryId(Long categoryId);
    List<ProductEntity> findByOwnerName(String name);
    List<ProductEntity> findByCategoryIdAndPriceGreaterThan(Long categoryId, Double price); /// precio mayor a
    
}
