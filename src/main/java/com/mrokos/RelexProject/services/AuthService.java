package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.dtos.UserDto;
import com.mrokos.RelexProject.dtos.UserResponseDto;
import com.mrokos.RelexProject.entities.User;
import com.mrokos.RelexProject.exceptions.AuthExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<?> registration (UserDto userDto){
        if(!userDto.getUserPassword().equals(userDto.getConfirmPassword())){
            return new ResponseEntity<>(new AuthExeption(HttpStatus.BAD_REQUEST.value(),"Пароли не совпадают"), HttpStatus.UNAUTHORIZED);
        }
        if(userService.findByEmail(userDto.getEmail()).isPresent()){
            return new ResponseEntity<>(new AuthExeption(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким email уже существует"), HttpStatus.UNAUTHORIZED);
        }
        User user = userService.createUser(userDto);
        return ResponseEntity.ok(new UserResponseDto(user.getId(),user.getUsername(), user.getEmail()));
    }
}
