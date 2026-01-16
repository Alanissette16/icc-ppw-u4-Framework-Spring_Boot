package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;

public class ProductResponseDto {
    public Long id;
    public String name;
    public String description;
    public Double price;
    public int stock;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    //PARTE A
    // aparezca sus categorias y su dueño
    public UserSummaryDTO user;
    // public CategoriaResponseDTO category;
    
    //PARTE B
    public List<CategoriaResponseDTO> categories;

    public static class UserSummaryDTO {
        public Long id;
        public String username;
        public String email;
    }
}
