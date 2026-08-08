package ru.ilezzov.group.flowgoods.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.ilezzov.group.flowgoods.common.entity.BaseEntity;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class WorkspaceEntity extends BaseEntity {
    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;
}
