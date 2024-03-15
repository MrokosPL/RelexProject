package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.dtos.AuthDto;
import com.mrokos.RelexProject.dtos.TokenDto;
import com.mrokos.RelexProject.dtos.UserDto;
import com.mrokos.RelexProject.dtos.UserResponseDto;
import com.mrokos.RelexProject.entities.User;
import com.mrokos.RelexProject.exceptions.AppExeption;
import com.mrokos.RelexProject.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenUtils tokenUtils;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }

    public ResponseEntity<?> authorization(@RequestBody AuthDto authDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppExeption(HttpStatus.UNAUTHORIZED.value(), "Отказано в доступе"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authDto.getEmail());
        String token = tokenUtils.tokenGeneration(userDetails);
        return ResponseEntity.ok(new TokenDto(token));
    }

    public ResponseEntity<?> registration(UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppExeption(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.UNAUTHORIZED);
        }
        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new AppExeption(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким email уже существует"), HttpStatus.UNAUTHORIZED);
        }
        User user = userService.createUser(userDto);
        return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
