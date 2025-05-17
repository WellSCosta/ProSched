package com.wellscosta.ProSched.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellscosta.ProSched.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
