package com.dkserver.danielServer.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String password;
}
