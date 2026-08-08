package ru.ilezzov.group.flowgoods.inventory.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByUuidAndWorkspaceId(final UUID uuid, final Long workspaceId);

    boolean existsByNameAndWorkspaceId(final String name, final Long workspaceId);
}
