package ru.ilezzov.group.flowgoods.tenant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import ru.ilezzov.group.flowgoods.common.entity.BaseEntity;

@Entity
@Table(name = "workspaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workspace extends BaseEntity {
    @Column(name = "name", nullable = false, length = 63)
    private String name;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
}
