package ru.ilezzov.group.flowgoods.iam.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.ilezzov.group.flowgoods.common.entity.AssignedIdEntity;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile extends AssignedIdEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "username", length = 63, nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", length = 63)
    private String firstName;

    @Column(name = "second_name", length = 63)
    private String secondName;

    @Column(name = "avatar_url")
    private String avatarUrl;
}
