package ru.ilezzov.group.flowgoods.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.iam.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUuid(final UUID uuid);

    Optional<User> findByEmail(final String email);

    @Query("SELECT u.id FROM User u WHERE u.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") final UUID uuid);

    boolean existsByEmail(final String email);

    boolean existsByProfileUsername(final String profileUsername);

}
