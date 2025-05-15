package com.wellscosta.ProSched.model;

import com.wellscosta.ProSched.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe entidade JPA de usuarios
 * Se classifica entre profissional ou cliente através do enum Role
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private int idade;
    private String email;
    private String senha;
    private Role role;
}
