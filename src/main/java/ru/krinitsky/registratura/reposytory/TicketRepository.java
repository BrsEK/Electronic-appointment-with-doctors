package ru.krinitsky.registratura.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krinitsky.registratura.domain.Doctor;
import ru.krinitsky.registratura.domain.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {


    List<Ticket> findByDate(LocalDate date);

    Optional<Ticket> findByDateAndTime(LocalDate date, LocalTime time);

    List<Ticket> findByDoctor(Doctor doctor);

    List<Ticket> findByDoctorAndDate(Doctor doctor, LocalDate localDate);
}
