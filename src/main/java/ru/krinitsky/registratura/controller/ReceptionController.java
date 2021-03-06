package ru.krinitsky.registratura.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krinitsky.registratura.domain.Ticket;
import ru.krinitsky.registratura.service.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/reception")
public class ReceptionController {
    private final ReceptionService receptionService;
    private final TicketService ticketService;
    private final DoctorService doctorService;
    private final SubscriberService subscriberService;
    private  Map<String, ?> allAttributes;


    @Autowired
    public ReceptionController(
            ReceptionService receptionService,
            TicketService ticketService,
            SpecialisationService specialisationService,
            DoctorService doctorService, SubscriberService subscriberService) {
        this.receptionService = receptionService;
        this.ticketService = ticketService;
        this.doctorService = doctorService;
        this.subscriberService = subscriberService;
    }


    @GetMapping(value = "/")
    public String showIndex(Model model) {
        model.addAttribute("receptionist", receptionService.getReceptionistByLogin());
        return "reception/index";
    }


    @GetMapping(value = "/addTicket")
    public String showPageAddTicket(Model model) {
        model.addAttribute("receptionist", receptionService.getReceptionistByLogin());
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("tickets", ticketService.getSortByDateAndTimeTickets());
        model.addAttribute("doctors", doctorService.getDoctors());
        allAttributes = model.asMap();
        return "reception/add_tickets";
    }


    @PostMapping(value = "/addTicket")
    public String addTicket(@ModelAttribute("ticket") @Valid Ticket ticket, BindingResult bindingResult, Model model, @RequestParam("doctorId") long doctorId) {
        if (bindingResult.hasErrors()){
            model.mergeAttributes(allAttributes);
            return "reception/add_tickets";
        }
        ticketService.addDoctorInTicket(ticket, doctorId);
        subscriberService.notifySubscribers(doctorService.getDoctorById(doctorId));
        ticketService.addTicket(ticket);
        return "redirect:/reception/addTicket";
    }


    @PostMapping(value = "/deleteTicket")
    public String deleteTicket(@RequestParam(name = "ticketId") long ticketId) {
        ticketService.deleteTicketById(ticketId);
        return "redirect:/reception/addTicket";
    }


    @GetMapping(value = "/tickets")
    public String showTickets(Model model) {
        model.addAttribute("receptionist", receptionService.getReceptionistByLogin());
        model.addAttribute("tickets", ticketService.getNewTickets());
        return "/reception/tickets";
    }


    @PostMapping(value = "/tickets", params = "accept")
    public String acceptTicket(@RequestParam(name = "ticketId") long ticketId) {
        ticketService.acceptTicket(ticketId);
        return "redirect:/reception/tickets";
    }


    @PostMapping(value = "/tickets", params = "reject")
    public String rejectTicket(@RequestParam(name = "ticketId") long ticketId) {
        ticketService.rejectTicket(ticketId);
        return "redirect:/reception/tickets";
    }
}
