package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.entities.User;
import com.mrokos.RelexProject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
@Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public Optional<User> findByEmail(String email){
    return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с такой почтой не найден")));
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
            user.getUserPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRoleName())));
    }
}

