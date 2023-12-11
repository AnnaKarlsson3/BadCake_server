package com.dkserver.danielServer.dto;

import com.dkserver.danielServer.models.UserEntity;
import com.dkserver.danielServer.security.CustomUserDetailsService;
import lombok.Data;

import java.util.Optional;

@Data
public class AuthResponseDto {
    //return access token - responseDto
    private String accessToken;
    private String tokenType = "Bearer ";
    private Optional<UserEntity> user;

    public AuthResponseDto(String accessToken, Optional<UserEntity> user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}

