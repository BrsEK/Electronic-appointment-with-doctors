package ru.krinitsky.registratura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krinitsky.registratura.service.DoctorService;
import ru.krinitsky.registratura.service.TicketService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final TicketService ticketService;


    @Autowired
    public DoctorController(DoctorService doctorService, TicketService ticketService) {
        this.doctorService = doctorService;
        this.ticketService = ticketService;
    }


    @GetMapping(value = "/")
    public String showIndex(Model model) {
        model.addAttribute("ticketsToday",
                ticketService.getDoctorTicketsContainsClients(doctorService.getDoctorByLogin().getId()));
        model.addAttribute("doctor", doctorService.getDoctorByLogin());
        return "/doctor/index";
    }


    @PostMapping(value = "removeClient")
    public String removeClient(@RequestParam("ticketId") long ticketId) {
        ticketService.removeTicketById(ticketId);
        return "redirect:/doctor/";
    }

}
