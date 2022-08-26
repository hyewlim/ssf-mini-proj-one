package com.project.controller;

import com.project.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

//    @RequestMapping (value ="/login", method = {RequestMethod.GET, RequestMethod.POST})
//    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
//                                   @RequestParam(value = "logout", required = false) String logout,
//                                   Model model) {
//        String errorMessage = null;
//        if (error != null)
//            errorMessage = "Username or Password is incorrect!";
//
//        if (logout != null)
//            errorMessage = "You have been successfully logged out!";
//
//        model.addAttribute("errorMessage", errorMessage);
//
//        return "login";
//    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/create-user")
    public String createUser(Model model){
        model.addAttribute("user", new User());
        return "create-user";
    }

    @PostMapping("/create-user")
    public String userSubmit(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);
        return "create-user-success";
    }

}
