package ru.ilezzov.group.flowgoods.inventory.entity.supplier;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.ilezzov.group.flowgoods.inventory.entity.WorkspaceEntity;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Supplier extends WorkspaceEntity {
    @Column(name = "name", length = 63, nullable = false)
    private String name;

    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Contact contact;

    public void setContact(Contact contact) {
        this.contact = contact;
        if (contact != null) {
            contact.setSupplier(this);
        }
    }
}
