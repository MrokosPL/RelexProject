package com.mrokos.RelexProject.services;

import com.mrokos.RelexProject.entities.Role;
import com.mrokos.RelexProject.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole(){
        return roleRepository.findByName("ROLE_OWNER").get();
    }
}
