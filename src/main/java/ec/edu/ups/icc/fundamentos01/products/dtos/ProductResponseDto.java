package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDTO;

public class ProductResponseDto {
    public Long id;
    public String name;
    public String description;
    public Double price;
    public int stock;
    public LocalDateTime createdAt;

    // aparezca sus categorias y ssu dueño
    public UserSummaryDTO user;

    public CategoriaResponseDTO category;

    public static class UserSummaryDTO {
        public Long id;
        public String username;
        public String email;
    }
}
