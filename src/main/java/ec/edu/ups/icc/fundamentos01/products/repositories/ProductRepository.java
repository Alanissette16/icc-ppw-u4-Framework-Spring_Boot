package ec.edu.ups.icc.fundamentos01.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);
}
