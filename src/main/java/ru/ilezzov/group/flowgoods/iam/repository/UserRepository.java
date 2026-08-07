package ru.ilezzov.group.flowgoods.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilezzov.group.flowgoods.iam.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUuid(final UUID uuid);

    Optional<User> findByEmail(final String email);

    boolean existsByEmail(final String email);

    boolean existsByProfileUsername(final String profileUsername);

}
