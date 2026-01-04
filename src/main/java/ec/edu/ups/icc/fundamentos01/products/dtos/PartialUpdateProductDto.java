package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class PartialUpdateProductDto {

    @Size(min = 3)
    public String name;
    public String description;
    @Min(0)
    public Double price;
    @Min(0)
    public Integer stock;
}
