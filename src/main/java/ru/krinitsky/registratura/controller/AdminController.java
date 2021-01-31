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


@Controller
@RequestMapping("/admin")
public class AdminController {
    private DoctorService doctorService;
    private SpecialisationService specialisationService;
    private ReceptionService receptionService;


    @Autowired
    public AdminController(DoctorService doctorService,
                           SpecialisationService specialisationService,
                           ReceptionService receptionService) {
        this.doctorService = doctorService;
        this.specialisationService = specialisationService;
        this.receptionService = receptionService;
    }


    // Метод открывает главную страницу администратора
    @GetMapping(value = "/")
    public String showIndex() {
        return "/admin/index";
    }


    // Открывает окно добавления врачей в базу данных
    @GetMapping(value = "/doctors")
    public String showAddDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("doctors", doctorService.getDoctors());
        model.addAttribute("specialisations", specialisationService.getSpecialisations());
        return "/admin/doctors";
    }


    // Метод привязывает врачей к специальности и добавляет их в базу
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


    // Метод удаляет врача из базы вместе с его аккаунтом
    @PostMapping(value = "/deleteDoctor")
    public String deleteDoctor(@RequestParam(name = "doctorEmail") String doctorEmail) {
        doctorService.removeDoctorWithAccount(doctorEmail);
        return "redirect:/admin/doctors";
    }


    // Метод открывает окно добавления специализации
    @GetMapping(value = "/specialisations")
    public String showSpecialisations(Model model) {
        model.addAttribute("specialisation", new Specialisation());
        model.addAttribute("specialisations", specialisationService.getSpecialisations());
        model.addAttribute("spec", new User());
        return "/admin/specialisations";
    }


    // Метод добавляет специализацию  и возвращает страницу
    @PostMapping(value = "/addSpecialisation")
    public String addSpecialisation(@ModelAttribute("specialisation") @Valid Specialisation specialisation,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/specialisations";
        }
        specialisationService.addSpecialisation(specialisation);
        return "redirect:/admin/specialisations";
    }


    // Метод удаляет специализацию  и возвращает страницу
    @PostMapping(value = "/deleteSpecialisation")
    public String deleteSpecialisation(@RequestParam("specialisationId") long specialisationId) {
        specialisationService.deleteSpecialisations(specialisationId);
        return "redirect:/admin/specialisations";
    }


    // Метод открывает окно добавления работников регистратуры
    @GetMapping(value = "/receptionist")
    public String showReceptionist(Model model) {
        model.addAttribute("receptionist", new Receptionist());
        model.addAttribute("receptionists", receptionService.getReceptionists());
        return "/admin/receptionists";
    }


    // Метод добавляет работника регистратуры  и возвращает страницу
    @PostMapping(value = "/addReceptionist")
    public String addReceptionist(@ModelAttribute("receptionist") @Valid Receptionist receptionist, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/receptionists";
        }
        receptionService.addReceptionist(receptionist);
        return "redirect:/admin/receptionist";
    }


    // Метод удаляем работников регистратуры из базы и возвращает страницу
    @PostMapping(value = "/deleteReceptionist")
    public String deleteReceptionist(@RequestParam("receptionistId") long receptionistId) {
        receptionService.deleteReceptionist(receptionistId);
        return "redirect:/admin/receptionist";
    }
}
