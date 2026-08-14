package ru.ilezzov.group.flowgoods.iam.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.general.NotNullableException;
import ru.ilezzov.group.flowgoods.iam.exception.user.UserNotFoundException;
import ru.ilezzov.group.flowgoods.iam.entity.User;
import ru.ilezzov.group.flowgoods.iam.repository.UserRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserResolver {
    private final UserRepository repository;

    public User resolveUserById(final Long id) {
        if (id == null) {
            throw new NotNullableException("id");
        }

        return this.repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User resolveUserByUuid(final UUID uuid) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        return this.repository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }

    public User resolveUserByEmail(final String email) {
        if (email == null) {
            throw new NotNullableException("email");
        }

        return this.repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public Long resolveUserIdByUuid(final UUID uuid) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        return this.repository.findIdByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }
}
