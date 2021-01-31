package ru.krinitsky.registratura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krinitsky.registratura.service.DoctorService;
import ru.krinitsky.registratura.service.SpecialisationService;
import ru.krinitsky.registratura.service.TicketService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private DoctorService doctorService;
    private TicketService ticketService;


    @Autowired
    public DoctorController(DoctorService doctorService,
                            SpecialisationService specialisationService, TicketService ticketService) {
        this.doctorService = doctorService;
        this.ticketService = ticketService;
    }


    // Метод открывает главную страницу доктора
    @GetMapping(value = "/")
    public String showIndex(Model model) {
        model.addAttribute("ticketsToday",
                ticketService.getDoctorTicketsContainsClients(doctorService.getDoctorByLogin().getId()));
        model.addAttribute("doctor", doctorService.getDoctorByLogin());
        return "/doctor/index";
    }

    // Метод удаляет талон, а в месте с ним автоматически удаляется клиент
    @PostMapping(value = "removeClient")
    public String removeClient(@RequestParam("ticketId") long ticketId){
        ticketService.removeTicketById(ticketId);
        return "redirect:/doctor/";
    }

}
