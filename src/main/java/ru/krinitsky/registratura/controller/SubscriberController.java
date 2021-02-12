package ru.krinitsky.registratura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krinitsky.registratura.domain.Client;
import ru.krinitsky.registratura.domain.Subscriber;
import ru.krinitsky.registratura.service.DoctorService;
import ru.krinitsky.registratura.service.SubscriberService;

import javax.validation.Valid;

@Controller
@RequestMapping("/subscriber")
public class SubscriberController {

    private DoctorService doctorService;
    private SubscriberService subscriberService;


    @Autowired
    public SubscriberController(DoctorService doctorService, SubscriberService subscriberService) {
        this.doctorService = doctorService;
        this.subscriberService = subscriberService;
    }


    //Метод открывает страницу подписки на врача
    @GetMapping(value = "/")
    public String showSubscribePage(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("doctors", doctorService.getDoctors());
        return "/subscriber/index";
    }


    // Метод выполняет подписку на врача
    @PostMapping(value = "/subscribe")
    public String subscribe(@ModelAttribute("subscriber") @Valid Subscriber subscriber, BindingResult bindingResult, @RequestParam("doctorId") long docId) {
        if (bindingResult.hasErrors()){
            return "/subscriber/index";
        }
        subscriberService.createSubscriber(subscriber.getEmail(), docId);
        return "redirect:/subscriber/";
    }
}
