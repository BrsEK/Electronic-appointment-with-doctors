package ru.krinitsky.registratura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.krinitsky.registratura.domain.User;
import ru.krinitsky.registratura.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationForm(User user){
        return "registration";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(){
        return "login";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String regUser(
            @ModelAttribute("user") @Valid User user,
            @RequestParam("flexRadioDefault") int userId,
            BindingResult bindingResult,
            Model model

    )
    {
        if (bindingResult.hasErrors()){
            return "registration";
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("confirmError", "Пароли не совпадают");
            return "registration";
        }

        if (!userService.saveUser(user, userId)){
            model.addAttribute("usernameError",
                    "Пользователь с данным логином уже существует");
            return "registration";
        }
        return "/admin/index";
    }

}
