package ru.krinitsky.registratura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krinitsky.registratura.domain.Client;
import ru.krinitsky.registratura.domain.Doctor;
import ru.krinitsky.registratura.domain.Ticket;
import ru.krinitsky.registratura.service.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/client")
public class ClientController {

    private SpecialisationService specialisationService;
    private DoctorService doctorService;
    private TicketService ticketService;
    private ClientService clientService;
    private SubscriberService subscriberService;


    @Autowired
    public ClientController(SpecialisationService specialisationService, DoctorService doctorService,
                            TicketService ticketService, ClientService clientService,
                            SubscriberService subscriberService) {
        this.specialisationService = specialisationService;
        this.doctorService = doctorService;
        this.ticketService = ticketService;
        this.clientService = clientService;
        this.subscriberService = subscriberService;
    }


    // Метод открывает главную страницу клиента
    @GetMapping(value = "/")
    public String showIndexPage(Model model) {
        model.addAttribute("specialisations", specialisationService.getSpecialisations());
        model.addAttribute("client", new Client());
        return "client/index";
    }


    // Метод возвращает докторов для выпадающего списка
    @GetMapping(value = "/doctors")
    @ResponseBody
    public Set<Doctor> getDoctors(@RequestParam("specId") long specialisationId) {
        Set<Doctor> doctors = doctorService.getDoctorsBySpecialisation(specialisationId);
        return doctors;
    }


    // Метод возвращает талоны
    @GetMapping(value = "/dates")
    @ResponseBody
    public Set<Ticket> changeTickets(@RequestParam("docId") long doctorId) {
        Set<Ticket> tickets = ticketService.getTicketsAtDoctor(doctorId);
        return tickets;
    }


    // Метод возвращает талоны на одного врача на одну дату
    @GetMapping(value = "/times")
    @ResponseBody
    public List<Ticket> getDate(@RequestParam("ticketId") long id) {
        return ticketService.getTicketByDoctorAndHisDate(id);
    }


    //Метод регистрирует клиента в талоне
    @PostMapping(value = "/addClientInTicket")
    public String register(@RequestParam("ticketId") long ticketId, @ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/client/index";
        }
        ticketService.addClientInTicket(client, ticketId);
        return "/client/well_done";
    }


    //Метод открывает страницу подписки на врача
    @GetMapping(value = "/subscribe")
    public String showSubscribePage(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("doctors", doctorService.getDoctors());
        return "/client/subscribe";
    }


    // Метод выполняет подписку на врача
    @PostMapping(value = "/subscribe")
    public String subscribe(@ModelAttribute("client") Client client, @RequestParam("doctorId") long docId) {
        subscriberService.createSubscriber(client.getEmail(), docId);
        return "redirect:/client/";
    }

}
