package ua.ithillel.javapro.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ithillel.javapro.domain.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

