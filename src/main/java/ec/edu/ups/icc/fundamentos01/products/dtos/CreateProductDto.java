package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateProductDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    public String name;
    public String description;
    @Positive(message = "El precio no puede ser negativo")
    public double price;
    @Positive(message = "El stock no puede ser negativo")
    public int stock;
}
