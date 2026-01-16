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
    
    // ===================== MANY-TO-ONE (1:N) - COMENTADO =====================
    // List<ProductEntity> findByCategoryId(Long categoryId);
    
    // ===================== MANY-TO-MANY (N:N) =====================
    // Búsqueda de productos por categoría con precio mayor a
    @Query("""
        SELECT DISTINCT p FROM ProductEntity p
        JOIN p.categories c
        WHERE c.id = :categoryId AND p.price > :price
    """)
    List<ProductEntity> findByCategoryIdAndPriceGreaterThan(@Param("categoryId") Long categoryId, @Param("price") Double price);
    
    // ===================== FUTURAS MEJORAS =====================
    // @Query("""
    //     SELECT COUNT(p)
    //     FROM ProductEntity p
    //     JOIN p.categories c
    //     WHERE c.id = :categoryId
    // """)
    // long countProductsByCategory(@Param("categoryId") Long categoryId);
}
