package com.mrokos.RelexProject.controllers;


import com.mrokos.RelexProject.dtos.UserResponseDto;
import com.mrokos.RelexProject.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public List<UserResponseDto> showAllUsers(){
        return userService.showAllUsers();
    }
}
