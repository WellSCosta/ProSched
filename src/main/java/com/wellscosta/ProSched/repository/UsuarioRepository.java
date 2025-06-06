package com.wellscosta.ProSched.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellscosta.ProSched.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);

    @Query("SELECT u.id FROM Usuario u WHERE u.email = :email")
    Long findIdByEmail(@Param("email") String email);
}
