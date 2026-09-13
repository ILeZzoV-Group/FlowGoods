package ru.ilezzov.group.flowgoods.inventory.entity.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ilezzov.group.flowgoods.inventory.entity.WorkspaceEntity;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Product;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends WorkspaceEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
