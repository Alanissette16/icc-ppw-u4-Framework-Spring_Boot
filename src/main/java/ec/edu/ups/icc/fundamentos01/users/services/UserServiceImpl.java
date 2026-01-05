package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;
import ec.edu.ups.icc.fundamentos01.users.models.User;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDto findOne(int id) {
        return userRepo.findById((long) id)
            .map(UserMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado")
        );
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
    // Regla: email único
    if (userRepo.findByEmail(dto.email).isPresent()) {
        throw new IllegalStateException("El email ya está registrado");
    }
    User user = User.fromDto(dto);
    UserEntity saved = userRepo.save(user.toEntity());
    return User.fromEntity(saved).toResponseDto();

    }

    @Override
    public UserResponseDto update(int id, UpdateUserDto dto) {
        UserEntity user = userRepo.findById((long) id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.name);
        user.setEmail(dto.email);

        return UserMapper.toResponse(userRepo.save(user));
    }

    @Override
    public UserResponseDto partialUpdate(int id, PartialUpdateUserDto dto) {
        UserEntity user = userRepo.findById((long) id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.name != null) user.setName(dto.name);
        if (dto.email != null) user.setEmail(dto.email);

        return UserMapper.toResponse(userRepo.save(user));
    }

    @Override
    public void delete(int id) {
        if (!userRepo.existsById((long) id)) {
            throw new RuntimeException("User not found");
        }
        userRepo.deleteById((long) id);
    }

}
