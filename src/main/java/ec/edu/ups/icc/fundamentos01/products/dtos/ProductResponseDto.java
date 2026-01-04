package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;

public class ProductResponseDto {
    public int id;
    public String name;
    public String description;
    public double price;
    public int stock;
    public LocalDateTime createdAt;
}
