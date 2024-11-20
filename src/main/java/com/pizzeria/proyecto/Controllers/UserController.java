package com.pizzeria.proyecto.Controllers;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public String details() {
        return "User/Details";
    }

    @GetMapping("/history")
    public String history() {
        return "User/History";
    }

    @GetMapping("/active")
    public String active() {
        return "User/Actives";
    }

    @GetMapping("/devolutions")
    public String devolutions() {
        return "User/Devolutions";
    }

}
