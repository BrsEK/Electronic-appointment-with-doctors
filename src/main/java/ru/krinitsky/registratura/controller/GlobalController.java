package ru.krinitsky.registratura.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {

    // Метод открывает главную страницу
    @GetMapping(name = "/")
    public String showIndex(){
        return "/index";
    }
}
