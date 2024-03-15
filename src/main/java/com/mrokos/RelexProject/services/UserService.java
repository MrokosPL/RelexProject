package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.config.ApiMapper;
import com.mrokos.RelexProject.dtos.ShowStatDto;
import com.mrokos.RelexProject.dtos.UserDto;
import com.mrokos.RelexProject.dtos.UserResponseDto;
import com.mrokos.RelexProject.entities.User;
import com.mrokos.RelexProject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с такой почтой не найден")));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getUserPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName())));
    }

    public User createUser(UserDto userDto) {
        ;
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setUserPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleService.getUserRole());
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден");
        }
        userRepository.deleteById(id);
    }

    public List<UserResponseDto> showAllUsers() {
        List<User> users = userRepository.findAll(Sort.by("username"));
        return users.stream().map(ApiMapper.INSTANCE::userToUserResponseDto).collect(Collectors.toList());
    }

    public Long getUserId(String email) {
        try {
            return findByEmail(email).get().getId();
        } catch (Exception e) {
            return null;
        }
    }
}

