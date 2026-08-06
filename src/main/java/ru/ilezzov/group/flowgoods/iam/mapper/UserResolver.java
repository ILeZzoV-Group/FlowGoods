package ru.ilezzov.group.flowgoods.iam.mapper;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.user.UserNotFoundException;
import ru.ilezzov.group.flowgoods.iam.entity.User;
import ru.ilezzov.group.flowgoods.iam.repository.UserRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserResolver {
    private final UserRepository repository;

    @Nullable
    public User resolveUser(final Long id) {
        if (id == null) {
            return null;
        }

        return this.repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Nullable
    public User resolveUser(final UUID uuid) {
        if (uuid == null) {
            return null;
        }

        return this.repository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }
}
