package io.flowtrack.gateway.repository;

import io.flowtrack.gateway.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
