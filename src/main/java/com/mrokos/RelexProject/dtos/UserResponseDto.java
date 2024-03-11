package com.mrokos.RelexProject.dtos;

import lombok.Data;

@Data
public class UserResponseDto {
    private Integer id;
    private String username;
    private String email;

    public UserResponseDto(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
