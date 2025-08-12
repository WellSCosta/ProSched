package com.wellscosta.ProSched.controller;

import com.wellscosta.ProSched.dto.autentificacao.AutenticacaoDTO;
import com.wellscosta.ProSched.dto.autentificacao.LoginResponseDTO;
import com.wellscosta.ProSched.dto.autentificacao.RegisterDTO;
import com.wellscosta.ProSched.infra.security.TokenService;
import com.wellscosta.ProSched.model.Usuario;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    /**
     * Login do usuário, verificando o email e senha passados.
     * @param autenticacaoDTO (email e senha)
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(autenticacaoDTO.email(), autenticacaoDTO.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    /**
     * Registro de novos usuários
     * @param registerDTO (email, senha, role)
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (this.repository.findByEmail(registerDTO.email()) != null) { //Verifica se o usuário já está cadastrado no banco
            return ResponseEntity.badRequest().build();
        }

        String senhaEncriptada = new BCryptPasswordEncoder().encode(registerDTO.senha()); //Faz a encriptação da senha para Hash

        Usuario novoUsuario = new Usuario(
                registerDTO.email(),
                senhaEncriptada,
                registerDTO.role(),
                registerDTO.nome(),
                registerDTO.sobrenome(),
                registerDTO.idade()
        );
        this.repository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
