package ru.ilezzov.group.flowgoods.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> { }
