package com.example.tutoudec.dto.auth;

import com.example.tutoudec.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String userName;
    private Usuario.Role role;
    private Long userId;
}
