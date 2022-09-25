package dev.marvan.jwtdemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @Secured()
    @GetMapping
    public String home(Principal principal) {
        return "Hello %s".formatted(principal.getName());
    }
}
