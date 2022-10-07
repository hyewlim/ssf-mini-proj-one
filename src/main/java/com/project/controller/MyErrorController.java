package com.project.controller;

import com.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @Autowired
    private User user;

    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("user", user);

        return "error";
    }
}
