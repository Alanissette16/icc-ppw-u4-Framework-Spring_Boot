package ec.edu.ups.icc.fundamentos01.categories.services;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDTO;

@Service
public interface CategoriaService {
    
    CategoriaResponseDTO save(CreateCategoriaDTO categoria);

}
