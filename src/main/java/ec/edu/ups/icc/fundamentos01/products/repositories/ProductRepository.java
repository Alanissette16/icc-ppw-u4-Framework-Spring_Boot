package ec.edu.ups.icc.fundamentos01.products.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    
    boolean existsByName(String name);
    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findByOwnerId(Long userId);
    List<ProductEntity> findByOwnerName(String name);
    // Parte A
    List<ProductEntity> findByCategoryId(Long categoryId);
    
    // Parte B
    List<ProductEntity> findByCategoryIdAndPriceGreaterThan(Long categoryId, Double price); /// precio mayor a
    
    //Parte C
    // @Query("""
    //     SELECT COUNT(p)
    //     FROM ProductEntity p
    //     JOIN p.categories c
    //     WHERE c.id = :categoryId
    // """)
    // long countProductsByCategory(@Param("categoryId") Long categoryId);
}
