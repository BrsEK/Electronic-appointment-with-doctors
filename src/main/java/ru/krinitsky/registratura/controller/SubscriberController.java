package ru.krinitsky.registratura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krinitsky.registratura.domain.Subscriber;
import ru.krinitsky.registratura.service.DoctorService;
import ru.krinitsky.registratura.service.SubscriberService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/subscriber")
public class SubscriberController {

    private final DoctorService doctorService;
    private final SubscriberService subscriberService;
    private Map<String, ?> attributes;


    @Autowired
    public SubscriberController(DoctorService doctorService, SubscriberService subscriberService) {
        this.doctorService = doctorService;
        this.subscriberService = subscriberService;
    }


    @GetMapping(value = "/")
    public String showSubscribePage(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("doctors", doctorService.getDoctors());
        attributes = model.asMap();
        return "/subscriber/index";
    }


    @PostMapping(value = "/subscribe")
    public String subscribe(@ModelAttribute("subscriber") @Valid Subscriber subscriber, BindingResult bindingResult,
                            @RequestParam("doctorId") long docId, Model model) {
        if (bindingResult.hasErrors()){
            model.mergeAttributes(attributes);
            return "/subscriber/index";
        }
        subscriberService.createSubscriber(subscriber.getEmail(), docId);
        return "redirect:/subscriber/";
    }
}
