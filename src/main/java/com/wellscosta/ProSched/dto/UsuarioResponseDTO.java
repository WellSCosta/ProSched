package com.wellscosta.ProSched.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private Integer idade;
    private String email;
    private String role;
}
