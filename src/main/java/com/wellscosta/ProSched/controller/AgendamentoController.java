package com.wellscosta.ProSched.controller;

import com.wellscosta.ProSched.dto.AgendamentoDisponibilidadeRequestDTO;
import com.wellscosta.ProSched.dto.AgendamentoRequestDTO;
import com.wellscosta.ProSched.infra.security.TokenService;
import com.wellscosta.ProSched.infra.security.UsuarioService;
import com.wellscosta.ProSched.model.Agendamento;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import com.wellscosta.ProSched.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/solicitar")
    public ResponseEntity solicitarAgendamento(@RequestBody @Valid AgendamentoDisponibilidadeRequestDTO requestDTO, @RequestHeader("Authorization") String token) {
        Long usuarioId = getUsuarioId(token);

        Agendamento agendamento = agendamentoService.solicitarAgendamento(requestDTO, usuarioId);
        return ResponseEntity.ok(agendamento);
    }

    //TODO tentar trocar esses métodos repetidos dos controllers para uma classe
    private Long getUsuarioId(String token) {
        String jwt = token.replace("Bearer ", "");

        //Resgata o email do token
        String email = tokenService.validarToken(jwt);
        //Buscar usuário pelo email
        return usuarioRepository.findIdByEmail(email);
    }
}
