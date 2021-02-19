package ru.krinitsky.registratura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krinitsky.registratura.domain.Doctor;
import ru.krinitsky.registratura.domain.Receptionist;
import ru.krinitsky.registratura.domain.Specialisation;
import ru.krinitsky.registratura.domain.User;
import ru.krinitsky.registratura.service.DoctorService;
import ru.krinitsky.registratura.service.ReceptionService;
import ru.krinitsky.registratura.service.SpecialisationService;

import javax.validation.Valid;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final DoctorService doctorService;
    private final SpecialisationService specialisationService;
    private final ReceptionService receptionService;
    private Map<String, ?> receptionAttributes;


    @Autowired
    public AdminController(DoctorService doctorService,
                           SpecialisationService specialisationService,
                           ReceptionService receptionService) {
        this.doctorService = doctorService;
        this.specialisationService = specialisationService;
        this.receptionService = receptionService;
    }



    @GetMapping(value = "/")
    public String showIndex() {
        return "/admin/index";
    }


    @GetMapping(value = "/doctors")
    public String showAddDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("specialisations", specialisationService.getSpecialisations());
        return "/admin/doctors";
    }


    @PostMapping(value = "/addDoctor")
    public String addDoctor(@ModelAttribute("doctor") @Valid Doctor doctor,
                            BindingResult bindingResult,
                            @RequestParam(name = "specialisationId") long specialisationId) {
        if (bindingResult.hasErrors()) {
            return "/admin/doctors";
        }
        Specialisation specialisation = specialisationService.getSpecialisationById(specialisationId);
        doctor.setSpecialisation(specialisation);
        doctorService.addDoctor(doctor);
        return "redirect:/admin/doctors";
    }


    @PostMapping(value = "/deleteDoctor")
    public String deleteDoctor(@RequestParam(name = "doctorEmail") String doctorEmail) {
        doctorService.removeDoctorWithAccount(doctorEmail);
        return "redirect:/admin/doctors";
    }


    @GetMapping(value = "/specialisations")
    public String showSpecialisations(Model model) {
        model.addAttribute("specialisation", new Specialisation());
        model.addAttribute("specialisations", specialisationService.getSpecialisations());
        model.addAttribute("spec", new User());
        return "/admin/specialisations";
    }


    @PostMapping(value = "/addSpecialisation")
    public String addSpecialisation(@ModelAttribute("specialisation") @Valid Specialisation specialisation,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/specialisations";
        }
        specialisationService.addSpecialisation(specialisation);
        return "redirect:/admin/specialisations";
    }


    @PostMapping(value = "/deleteSpecialisation")
    public String deleteSpecialisation(@RequestParam("specialisationId") long specialisationId) {
        specialisationService.deleteSpecialisations(specialisationId);
        return "redirect:/admin/specialisations";
    }


    @GetMapping(value = "/receptionist")
    public String showReceptionist(Model model) {
        model.addAttribute("receptionist", new Receptionist());
        model.addAttribute("receptionists", receptionService.getReceptionists());
        receptionAttributes = model.asMap();
        return "/admin/receptionists";
    }


    @PostMapping(value = "/addReceptionist")
    public String addReceptionist(@ModelAttribute("receptionist") @Valid Receptionist receptionist,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(receptionAttributes);
            return "/admin/receptionists";
        }
        receptionService.addReceptionist(receptionist);
        return "redirect:/admin/receptionist";
    }


    @PostMapping(value = "/deleteReceptionist")
    public String deleteReceptionist(@RequestParam("receptionistId") long receptionistId) {
        receptionService.deleteReceptionist(receptionistId);
        return "redirect:/admin/receptionist";
    }
}
