package ru.krinitsky.registratura.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krinitsky.registratura.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
