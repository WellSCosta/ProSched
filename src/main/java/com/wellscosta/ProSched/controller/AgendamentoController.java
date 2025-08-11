package com.wellscosta.ProSched.controller;

import com.wellscosta.ProSched.dto.AgendamentoDisponibilidadeRequestDTO;
import com.wellscosta.ProSched.dto.AgendamentoResponseDTO;
import com.wellscosta.ProSched.infra.security.TokenService;
import com.wellscosta.ProSched.model.Agendamento;
import com.wellscosta.ProSched.model.Usuario;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import com.wellscosta.ProSched.service.AgendamentoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/solicitar")
    public ResponseEntity<AgendamentoResponseDTO> solicitarAgendamento(@RequestBody @Valid AgendamentoDisponibilidadeRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        Agendamento agendamento = agendamentoService.solicitarAgendamento(requestDTO, usuario);
        AgendamentoResponseDTO responseDTO = mapper.map(agendamento, AgendamentoResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }
}
