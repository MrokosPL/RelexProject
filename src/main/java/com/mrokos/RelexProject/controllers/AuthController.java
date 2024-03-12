package com.mrokos.RelexProject.controllers;


import com.mrokos.RelexProject.config.JwtFilter;
import com.mrokos.RelexProject.dtos.AuthDto;
import com.mrokos.RelexProject.dtos.UserDto;
import com.mrokos.RelexProject.services.AuthService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
        return authService.registration(userDto);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authorize (@RequestBody AuthDto authDto){
        return authService.authorization(authDto);
    }
}
