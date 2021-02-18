package ru.krinitsky.registratura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.krinitsky.registratura.domain.Doctor;
import ru.krinitsky.registratura.domain.Specialisation;
import ru.krinitsky.registratura.domain.User;
import ru.krinitsky.registratura.reposytory.DoctorRepository;
import ru.krinitsky.registratura.reposytory.SpecialisationRepository;
import ru.krinitsky.registratura.reposytory.TicketRepository;
import ru.krinitsky.registratura.reposytory.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialisationRepository specialisationRepository;
    private final UserRepository userRepository;


    @Autowired
    public DoctorService(DoctorRepository doctorRepository, SpecialisationRepository specialisationRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.specialisationRepository = specialisationRepository;
        this.userRepository = userRepository;
    }


    // Метод находит по специализации докторов
    public Set<Doctor> getDoctorsBySpecialisation(long specialisationId) {
        Specialisation specialisation = specialisationRepository.findById(specialisationId).get();
        return specialisation.getDoctors();
    }


    // Метод ищет врача по id  и возвращает его
    public Doctor getDoctorById(long doctorId) {
        return doctorRepository.findById(doctorId).get();
    }


    // Метод возвращает всех врачей из базы данных
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }


    // Метод добавляет врача в базу данных
    // Проверяем на наличие доктора с таким же email
    public void addDoctor(Doctor doctor) {
        if (doctorRepository.findByEmail(doctor.getEmail()).isEmpty()) {
            doctorRepository.save(doctor);
        }
    }


    // Метод удаляет врача из базы
    public void removeDoctorWithAccount(String email) {
        User user = userRepository.findByUsername(email);
        userRepository.delete(user);
        Doctor doctor = doctorRepository.findByEmail(email).get();
        doctorRepository.delete(doctor);
    }


    // Метод возвращает врача по логину
    public Doctor getDoctorByLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return doctorRepository.findByEmail(name).get();
    }

}
