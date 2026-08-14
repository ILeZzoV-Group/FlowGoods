package ru.ilezzov.group.flowgoods.inventory.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.ilezzov.group.flowgoods.inventory.entity.WorkspaceEntity;

@Entity
@Table(name = "marketplaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Marketplace extends WorkspaceEntity {
    @Column(name = "name", length = 63, nullable = false)
    private String name;

    @Column(name = "url")
    private String url;
}
