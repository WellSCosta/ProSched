package com.wellscosta.ProSched.model;

import com.wellscosta.ProSched.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Classe entidade JPA de usuarios
 * Se classifica entre profissional ou cliente através do enum Role
 */

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private int idade;

    @Column(unique = true)
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Usuario(String email, String senha, Role role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) //Caso seja ADMIN terá acesso por todas as Roles
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_PROFISSIONAL"),
                    new SimpleGrantedAuthority("ROLE_CLIENTE"));

        else if (this.role == Role.PROFISSIONAL) //Caso seja PROFISSIONAL apenas acesso ao de profissional
            return List.of(new SimpleGrantedAuthority("ROLE_PROFISSIONAL"));

        else return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE")); //Caso seja CLIENTE apenas acesso ao de cliente
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
