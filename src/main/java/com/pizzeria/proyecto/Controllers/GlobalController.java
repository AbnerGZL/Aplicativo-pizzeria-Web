//package com.pizzeria.proyecto.Controllers;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//@ControllerAdvice
//public class GlobalController {
//
//    @ModelAttribute
//    public void addUserToModel(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
//            model.addAttribute("username", authentication.getName());
//        } else {
//            model.addAttribute("username", null);
//        }
//    }
//}
