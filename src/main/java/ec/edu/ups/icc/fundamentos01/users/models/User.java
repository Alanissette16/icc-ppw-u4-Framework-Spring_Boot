package ec.edu.ups.icc.fundamentos01.users.models;

import java.time.LocalDateTime;

import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class User {
    /// Variables de instancia
    private int id;
    private String name;
    private String email;
    private String password;
    public LocalDateTime createdAt;

    /// Constructores 
    public User(int id, String name, String email, String password, LocalDateTime createdAt) {

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nombre inválido");

        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido");

        if (password == null || password.length() < 8)
            throw new IllegalArgumentException("Password inválido");

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }
    // Getters y Setters
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ==================== FACTORY METHODS ====================

    public static User fromDto(CreateUserDto dto) {
        return new User(
            0,
            dto.name,
            dto.email,
            dto.password,
            LocalDateTime.now()
        );
    }

    public static User fromEntity(UserEntity entity) {
        return new User(
            entity.getId().intValue(),
            entity.getName(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getCreatedAt()
        );
    }

    // ==================== CONVERSION METHODS ====================

    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        if (this.id > 0) {
            entity.setId((long) this.id);
        }
        entity.setName(this.name);
        entity.setEmail(this.email);
        entity.setPassword(this.password);

        if (this.createdAt == null) {
        entity.setCreatedAt(LocalDateTime.now());
        } else {
        entity.setCreatedAt(this.createdAt);
        }

        return entity;
    }

    public UserResponseDto toResponseDto() {
        UserResponseDto dto = new UserResponseDto();
        dto.id = this.id;
        dto.name = this.name;
        dto.email = this.email;
        dto.createdAt = this.createdAt;
        return dto;
    }

    // ==================== UPDATE METHODS ====================

    public User update(UpdateUserDto dto) {
        this.name = dto.name;
        this.email = dto.email;
        this.password = dto.password;
        return this;
    }

    public User partialUpdate(PartialUpdateUserDto dto) {
        if (dto.name != null) this.name = dto.name;
        if (dto.email != null) this.email = dto.email;
        if (dto.password != null) this.password = dto.password;
        return this;
    }
}
