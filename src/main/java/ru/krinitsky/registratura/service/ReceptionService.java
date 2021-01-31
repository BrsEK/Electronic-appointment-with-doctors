package ru.krinitsky.registratura.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.krinitsky.registratura.domain.Doctor;
import ru.krinitsky.registratura.domain.Receptionist;
import ru.krinitsky.registratura.domain.Ticket;
import ru.krinitsky.registratura.domain.User;
import ru.krinitsky.registratura.reposytory.*;

import java.util.List;

@Service
public class ReceptionService {

    private DoctorRepository doctorRepository;
    private TicketRepository ticketRepository;
    private SpecialisationRepository specialisationRepository;
    private ReceptionRepository receptionRepository;
    private UserRepository userRepository;
    private  SubscriberService subscriberService;


    @Autowired
    public ReceptionService(DoctorRepository doctorRepository, TicketRepository ticketRepository, SpecialisationRepository specialisationRepository, ReceptionRepository receptionRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.ticketRepository = ticketRepository;
        this.specialisationRepository = specialisationRepository;
        this.receptionRepository = receptionRepository;
        this.userRepository = userRepository;
    }


    // Метод возвращает всех талоны из базы данных
    public List<Ticket> getTickets(){
        return ticketRepository.findAll();
    }


    //Метод добавляет врача в талон
    public void addDoctorInTicket(long doctorId, Ticket ticket) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        ticket.setDoctor(doctor);
        ticketRepository.save(ticket);
    }


    // Метод добавляет работника регистратуры в базу данных
    // Метод также проверяет на наличие одинакового email
    public void addReceptionist(Receptionist receptionist){
        if (receptionRepository.findByEmail(receptionist.getEmail().toString()).isEmpty()){
            receptionRepository.save(receptionist);
        }
    }


    // Метод возвращает из базы данных всех работников регистратуры
    public List<Receptionist> getReceptionists(){
        return receptionRepository.findAll();
    }


    // Удалить работника регистратуры c его аккаунтом
    public void deleteReceptionist(long receptionistId){
        Receptionist receptionist = receptionRepository.findById(receptionistId).get();
        User user = userRepository.findByUsername(receptionist.getEmail());
        userRepository.delete(user);
        receptionRepository.delete(receptionist);
    }


    // Метод возвращает работника регистратуры по логину
    public  Receptionist getReceptionistByLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return receptionRepository.findByEmail(name).get();
    }
}

