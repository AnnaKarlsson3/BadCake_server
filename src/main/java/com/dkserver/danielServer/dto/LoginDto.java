package com.dkserver.danielServer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LoginDto {

    private String username;
    private String password;
}
