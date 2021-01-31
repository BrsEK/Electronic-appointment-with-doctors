package ru.krinitsky.registratura.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krinitsky.registratura.domain.Receptionist;

import java.util.Optional;

public interface ReceptionRepository extends JpaRepository<Receptionist, Long> {
    Optional<Receptionist> findByEmail(String email);
}
