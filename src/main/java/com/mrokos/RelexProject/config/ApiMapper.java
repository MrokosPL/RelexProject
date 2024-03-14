package com.mrokos.RelexProject.config;

import com.mrokos.RelexProject.dtos.UserResponseDto;
import com.mrokos.RelexProject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApiMapper {
    ApiMapper INSTANCE = Mappers.getMapper(ApiMapper.class);
    UserResponseDto userToUserResponseDto(User user);
}
