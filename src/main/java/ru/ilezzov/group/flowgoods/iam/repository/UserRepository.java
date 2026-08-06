package ru.ilezzov.group.flowgoods.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilezzov.group.flowgoods.iam.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
