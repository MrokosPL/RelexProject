package com.mrokos.RelexProject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {
    @GetMapping("/info")
    public String info(Principal principal){
        return principal.getName();
    }
    @GetMapping("/owner")
    public String ownerData(){
        return ("Owner data");
    }
    @GetMapping("/secured")
    public String securedData(){
        return ("Secured data");

    }

}
