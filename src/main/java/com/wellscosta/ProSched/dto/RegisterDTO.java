package com.wellscosta.ProSched.dto;

import com.wellscosta.ProSched.model.enums.Role;

public record RegisterDTO(String email, String senha, Role role, String nome, String sobrenome, Integer idade) {
}
