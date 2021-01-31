package ru.krinitsky.registratura.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krinitsky.registratura.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> getRoleByName(String name);
}
