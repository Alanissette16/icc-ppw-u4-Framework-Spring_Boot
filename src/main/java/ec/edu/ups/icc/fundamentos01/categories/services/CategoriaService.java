package ec.edu.ups.icc.fundamentos01.categories.services;




import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDTO;

public interface CategoriaService {
    
    CategoriaResponseDTO create(CreateCategoriaDTO categoria);
    
    // ===================== FUTURAS MEJORAS =====================
    // long countProducts(Long categoryId);
}
