package ec.edu.ups.icc.fundamentos01.users.mappers;

import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(CreateUserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.name);
        entity.setEmail(dto.email);
        entity.setPassword(dto.password);
        return entity;
    }

    public static UserResponseDto toResponse(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = entity.getId().intValue();
        dto.name = entity.getName();
        dto.email = entity.getEmail();
        return dto;
    }
}
