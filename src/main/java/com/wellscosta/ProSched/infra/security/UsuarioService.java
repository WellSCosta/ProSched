package com.wellscosta.ProSched.infra.security;

import com.wellscosta.ProSched.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private static TokenService tokenService;
    @Autowired
    private static UsuarioRepository usuarioRepository;

    public Long getUsuarioId(String token) {
        String jwt = token.replace("Bearer ", "");

        //Resgata o email do token
        String email = tokenService.validarToken(jwt);
        //Buscar usuário pelo email
        return usuarioRepository.findIdByEmail(email);
    }
}
