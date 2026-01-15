package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProductDto {

    @NotBlank
    @Size(min = 3)
    public String name;
    public String description;
    @Min(0)
    public double price;
    @Min(0)
    public int stock;

    public Long categoryId;
    /// No puedo actualizar el dueño del producto
    /// public Long userId;

}
