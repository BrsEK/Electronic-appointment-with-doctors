package ru.krinitsky.registratura.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krinitsky.registratura.domain.Specialisation;

public interface SpecialisationRepository extends JpaRepository<Specialisation, Long> {
}
