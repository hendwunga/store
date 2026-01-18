package com.hend.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToProducts() {
        return "redirect:/products"; // langsung ke product list
    }
}
