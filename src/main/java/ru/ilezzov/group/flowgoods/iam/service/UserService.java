package ru.ilezzov.group.flowgoods.iam.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.iam.dto.UserUpdateDto;
import ru.ilezzov.group.flowgoods.iam.exception.user.UserAlreadyExistsException;
import ru.ilezzov.group.flowgoods.iam.exception.user.UsernameAlreadyExistsException;
import ru.ilezzov.group.flowgoods.iam.dto.AuthResponseDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserCreateDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserResponseDto;
import ru.ilezzov.group.flowgoods.iam.entity.Profile;
import ru.ilezzov.group.flowgoods.iam.entity.User;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtProperties;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtService;
import ru.ilezzov.group.flowgoods.iam.mapper.UserMapper;
import ru.ilezzov.group.flowgoods.iam.resolver.UserResolver;
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

    private final JwtProperties jwtProperties;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(final Long id) {
        return this.mapper.toDto(
                this.userResolver.resolveUserById(id)
        );
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByUuid(final UUID uuid) {
        return this.mapper.toDto(
            this.userResolver.resolveUserByUuid(uuid)
        );
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(final String email) {
        return this.mapper.toDto(
                this.userResolver.resolveUserByEmail(email.toLowerCase().trim())
        );
    }

    public AuthResponseDto registerUser(final UserCreateDto dto) {
        final String email = dto.email().toLowerCase().trim();
        if (this.userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        final String username = dto.username().toLowerCase().trim();
        if (this.userRepository.existsByProfileUsername(username)) {
            throw new UsernameAlreadyExistsException(username);
        }

        final String passwordHash = this.passwordEncoder.encode(dto.password());
        final User registerUser = this.mapper.toEntity(dto, passwordHash, email, username);

        final UserResponseDto userResponseDto = this.mapper.toDto(
                this.userRepository.save(registerUser)
        );
        return new AuthResponseDto(
                this.jwtService.generate(userResponseDto.uuid()),
                this.jwtProperties.type(),
                this.jwtProperties.expiration(),
                userResponseDto
        );
    }

    public UserResponseDto updateUser(final UserUpdateDto dto, final UUID uuid) {
        final User user = this.userResolver.resolveUserByUuid(uuid);
        String normalizedUsername = null;

        if (dto.username() != null) {
            normalizedUsername = dto.username().toLowerCase().trim();

            if (!user.getProfile().getUsername().equalsIgnoreCase(normalizedUsername)) {
                if (this.userRepository.existsByProfileUsername(normalizedUsername)) {
                    throw new UsernameAlreadyExistsException(normalizedUsername);
                }
            }
        }

        this.mapper.updateEntity(dto, user, normalizedUsername);
        return this.mapper.toDto(user);
    }
}
