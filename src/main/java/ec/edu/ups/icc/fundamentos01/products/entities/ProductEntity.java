package ec.edu.ups.icc.fundamentos01.products.entities;

import java.util.HashSet;
import java.util.Set;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseModel {
    @Column(nullable = false, length = 200)
    private String name;
    @Column(length = 500)
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private Integer stock;

    /// Atributos Relacionales

    /// Con Usuarios donde un usuario puede tener muchos productos
    /// muchos productos pertenecen a un usuario
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner; /// -> ENTIDAD

    /// Con Categorias donde una categoria puede tener muchos productos
    /// muchos productos pertenecen a una categoria
    /// Descomentar para que funcione el PARTE A ManyToOne (1:N) ***************
    // @ManyToOne(optional = false, fetch = FetchType.LAZY) 
    // @JoinColumn(name = "category_id", nullable = false) 
    // private CategoryEntity category; 

    // PARTE B(N:N)
    @ManyToMany
    @JoinTable(name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categories = new HashSet<>();

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    // public CategoryEntity getCategory() {
    //     return category;
    // }

    // public void setCategory(CategoryEntity category) {
    //     this.category = category;
    // }
    

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }


}
