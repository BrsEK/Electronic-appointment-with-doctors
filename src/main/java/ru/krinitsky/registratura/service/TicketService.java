package ru.krinitsky.registratura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.krinitsky.registratura.domain.Client;
import ru.krinitsky.registratura.domain.Doctor;
import ru.krinitsky.registratura.domain.Ticket;
import ru.krinitsky.registratura.reposytory.DoctorRepository;
import ru.krinitsky.registratura.reposytory.SubscriberRepository;
import ru.krinitsky.registratura.reposytory.TicketRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final DoctorRepository doctorRepository;
    private final ClientService clientService;

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         DoctorRepository doctorRepository,
                         ClientService clientService,
                         SubscriberRepository subscriberRepository,
                         MailService mailService, DoctorService doctorService) {
        this.ticketRepository = ticketRepository;
        this.doctorRepository = doctorRepository;
        this.clientService = clientService;
    }


    // Метод возвращает все талоны отсортированные по времени и дате
    public List<Ticket> getSortByDateAndTimeTickets() {
        return ticketRepository.findAll(Sort.by("date").and(Sort.by("time")));
    }


    // Метод добавляет новый талон в базу
    // Исключаем регистрирования двух талонов на одно и тоже время и дату
    public void addTicket(Ticket ticket) {
        Optional<Ticket> optionalTicket = ticketRepository.findByDateAndTime(ticket.getDate(), ticket.getTime());
        if (!optionalTicket.isEmpty()) {
            Optional<Ticket> optionalTicketTrue = optionalTicket.filter(ticket1 -> ticket1.getDoctor().getEmail().equals(ticket.getDoctor().getEmail()));
            if (!optionalTicketTrue.isEmpty()) {
                return;
            }
        }
        ticketRepository.save(ticket);
    }


    // Метод привязывает доктора к талону
    public void addDoctorInTicket(Ticket ticket, long doctorId) {
        ticket.setDoctor(doctorRepository.findById(doctorId).get());
    }


    //Метод возвращает не подтвержденные талоны по выбранному доктору
    public Set<Ticket> getTicketsAtDoctor(long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        return doctor.getTickets().stream().filter(ticket -> !ticket.isConfirmed() && ticket.getClient() == null).collect(Collectors.toSet());
    }


    //Метод возвращает талоны c записанными в них клиентами по выбранному доктору
    public Set<Ticket> getDoctorTicketsContainsClients(long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        return doctor.getTickets().stream()
                .filter(ticket -> ticket.getClient() != null)
                .filter(ticket -> ticket.isConfirmed())
                .collect(Collectors.toSet());
    }


    //Метод возвращает талоны по выбранной дате
    public List<Ticket> getTicketByDate(String date) {
        return ticketRepository.findByDate(LocalDate.parse(date));
    }


    // Метод возвращает талоны на сегодня, отсортированные по времени
    public List<Ticket> getTicketForToday() {
        List<Ticket> tickets = ticketRepository.findByDate(LocalDate.now());
        tickets.sort(Comparator.comparing(Ticket::getTime));
        return tickets;
    }


    //Метод добавляет в талон клиента
    public void addClientInTicket(Client client, long ticketId) {
        clientService.addClient(client);
        Ticket ticket = ticketRepository.findById(ticketId).get();
        ticket.setClient(client);
        ticketRepository.save(ticket);
    }


    // Метод добавляет в талон клиента
    // Проверяем что клиент есть в талоне и статус талона не подтвержден
    public List<Ticket> getNewTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().filter((ticket) -> ticket.getClient() != null && ticket.isConfirmed())
                .collect(Collectors.toList());
    }


    // Удаление талона из базы, если он существует
    public void deleteTicketById(long id) {
        Ticket ticket = ticketRepository.findById(id).get();
        ticketRepository.delete(ticket);
    }


    // Метод удаляет клиента из талона и ставит устанавливает талону статус
    public void rejectTicket(long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        Client client = ticket.getClient();
        ticket.setClient(null);
        clientService.deleteClient(client);
        ticketRepository.save(ticket);
    }


    // Метод берет из базы талон и меняет флаг подтверждения на true
    public void acceptTicket(long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        ticket.setConfirmed(true);
        ticketRepository.save(ticket);
    }


    public Ticket getTicketById(long ticketId) {
        return ticketRepository.findById(ticketId).get();
    }


    // Метод находит талоны которые принадлежат одному врачу и которые относятся к одной дате
    public List<Ticket> getTicketByDoctorAndHisDate(long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        return ticketRepository.findByDoctorAndDate(ticket.getDoctor(), ticket.getDate());
    }


    // Метод  удаляет талон
    public void removeTicketById(long ticketId) {
        ticketRepository.delete(ticketRepository.findById(ticketId).get());
    }
}
