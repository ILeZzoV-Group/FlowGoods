package ru.ilezzov.group.flowgoods.iam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.exception.user.UserAlreadyExists;
import ru.ilezzov.group.flowgoods.iam.dto.UserCreateDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserResponseDto;
import ru.ilezzov.group.flowgoods.iam.entity.User;
import ru.ilezzov.group.flowgoods.iam.mapper.UserMapper;
import ru.ilezzov.group.flowgoods.iam.mapper.UserResolver;
import ru.ilezzov.group.flowgoods.iam.repository.UserRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserResolver userResolver;
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(final Long id) {
        return this.mapper.toDto(
                this.userResolver.resolveUser(id)
        );
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByUuid(final UUID uuid) {
        return this.mapper.toDto(
            this.userResolver.resolveUser(uuid)
        );
    }

    public UserResponseDto registerUser(final UserCreateDto dto) {
        final String email = dto.email().toLowerCase().trim();
        
        if (this.userRepository.existsByEmail(email)) {
            throw new UserAlreadyExists(email);
        }

        final String passwordHash = this.passwordEncoder.encode(dto.password());
        final User registerUser = User.builder()
                .email(email)
                .hash(passwordHash)
                .build();

        return this.mapper.toDto(
                this.userRepository.save(registerUser)
        );
    }
}
