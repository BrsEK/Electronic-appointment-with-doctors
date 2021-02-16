package ru.krinitsky.registratura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krinitsky.registratura.domain.Specialisation;
import ru.krinitsky.registratura.service.SpecialisationService;

@Controller
@RequestMapping("/specialisation")
public class SpecialisationController {

    private final SpecialisationService specialisationService;

    @Autowired
    public SpecialisationController(SpecialisationService specialisationService) {
        this.specialisationService = specialisationService;
    }


    @GetMapping(value = "/")
    public String addSpecialisation(Model model){
        model.addAttribute("specialisation", new Specialisation());
        model.addAttribute("specialisations", specialisationService.getSpecialisations());
        return "/reception/specialisations";
    }


    @PostMapping(value = "add_specialisation")
    public String addSpecialisation(@ModelAttribute("specialisation") Specialisation specialisation){
        specialisationService.addSpecialisation(specialisation);
        return "redirect:/specialisation/";
    }


    @PostMapping(value = "delete_specialisations")
    public String deleteSpecialisations(@RequestParam(name = "specialisationId") long id){
        specialisationService.deleteSpecialisations(id);
        return "redirect:/specialisation/";
    }

}
