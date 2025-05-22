package com.wellscosta.ProSched.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellscosta.ProSched.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
}
