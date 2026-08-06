package ru.ilezzov.group.flowgoods.iam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.exception.user.UserAlreadyExists;
import ru.ilezzov.group.flowgoods.iam.dto.RegisterUserDto;
import ru.ilezzov.group.flowgoods.iam.dto.ResponseUserDto;
import ru.ilezzov.group.flowgoods.iam.entity.User;
import ru.ilezzov.group.flowgoods.iam.mapper.UserMapper;
import ru.ilezzov.group.flowgoods.iam.mapper.UserResolver;
import ru.ilezzov.group.flowgoods.iam.repository.UserRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserResolver resolver;
    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public ResponseUserDto getUserById(final Long id) {
        return this.mapper.toDto(
                this.resolver.resolveUser(id)
        );
    }

    @Transactional(readOnly = true)
    public ResponseUserDto getUserByUUID(final UUID uuid) {
        return this.mapper.toDto(
            this.resolver.resolveUser(uuid)
        );
    }

    public ResponseUserDto registerUser(final RegisterUserDto dto) {
        final String email = dto.email().toLowerCase().trim();
        
        if (this.repository.existsByEmail(email)) {
            throw new UserAlreadyExists(email);
        }

        final String passwordHash = this.passwordEncoder.encode(dto.password());
        final User registerUser = User.builder()
                .email(email)
                .hash(passwordHash)
                .build();

        return this.mapper.toDto(
                this.repository.save(registerUser)
        );
    }
}
