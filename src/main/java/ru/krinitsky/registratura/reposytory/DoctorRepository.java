package ru.krinitsky.registratura.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krinitsky.registratura.domain.Doctor;

import java.util.Optional;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {

        Optional<Doctor> findByEmail(String email);


}
