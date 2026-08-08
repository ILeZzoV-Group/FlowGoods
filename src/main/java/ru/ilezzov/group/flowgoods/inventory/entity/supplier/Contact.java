package ru.ilezzov.group.flowgoods.inventory.entity.supplier;

import jakarta.persistence.*;
import lombok.*;
import ru.ilezzov.group.flowgoods.common.entity.AssignedIdEntity;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact extends AssignedIdEntity {
    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "link")
    private String link;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
