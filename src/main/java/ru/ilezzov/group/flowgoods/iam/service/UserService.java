package ru.ilezzov.group.flowgoods.iam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.exception.user.UserAlreadyExistsException;
import ru.ilezzov.group.flowgoods.common.exception.user.UsernameAlreadyExistsException;
import ru.ilezzov.group.flowgoods.iam.dto.AuthResponseDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserCreateDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserResponseDto;
import ru.ilezzov.group.flowgoods.iam.entity.Profile;
import ru.ilezzov.group.flowgoods.iam.entity.User;
import ru.ilezzov.group.flowgoods.iam.jwt.JwtProperties;
import ru.ilezzov.group.flowgoods.iam.jwt.JwtService;
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
                this.userResolver.resolveUserByEmail(email)
        );
    }

    public AuthResponseDto registerUser(final UserCreateDto dto) {
        final String email = dto.email().toLowerCase().trim();
        
        if (this.userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        if (this.userRepository.existsByProfileUsername(dto.username())) {
            throw new UsernameAlreadyExistsException(dto.username());
        }

        final String passwordHash = this.passwordEncoder.encode(dto.password());

        final User registerUser = User.builder()
                .email(email)
                .hash(passwordHash)
                .build();
        final Profile profile = Profile.builder()
                .username(dto.username())
                .build();
        registerUser.setProfile(profile);

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
}
