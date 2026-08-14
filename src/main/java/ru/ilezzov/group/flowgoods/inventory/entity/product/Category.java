package ru.ilezzov.group.flowgoods.inventory.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.ilezzov.group.flowgoods.inventory.entity.WorkspaceEntity;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends WorkspaceEntity {
    @Column(name = "name", length = 63, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
