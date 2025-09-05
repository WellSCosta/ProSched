package com.wellscosta.ProSched.controller;

import com.wellscosta.ProSched.dto.agendamento.AgendamentoByIdRequestDTO;
import com.wellscosta.ProSched.dto.agendamento.AgendamentoDisponibilidadeRequestDTO;
import com.wellscosta.ProSched.dto.agendamento.AgendamentoResponseDTO;
import com.wellscosta.ProSched.infra.security.TokenService;
import com.wellscosta.ProSched.model.Agendamento;
import com.wellscosta.ProSched.model.Usuario;
import com.wellscosta.ProSched.model.enums.StatusAgendamento;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import com.wellscosta.ProSched.service.AgendamentoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * Somente ROLE: Cliente
     */
    @PostMapping("/solicitar")
    public ResponseEntity<AgendamentoResponseDTO> solicitarAgendamento(@RequestBody @Valid AgendamentoDisponibilidadeRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {
        Agendamento agendamento = agendamentoService.solicitarAgendamento(requestDTO, usuario);
        return ResponseEntity.ok(converterParaDTO(agendamento));
    }

    /**
     * Somente ROLE: Profissional
     */
    @PutMapping("/confirmar/{id}")
    public ResponseEntity<AgendamentoResponseDTO> confirmarAgendamento(@PathVariable Long id) {
        Agendamento agendamento = agendamentoService.confirmarAgendamento(id);
        return ResponseEntity.ok(converterParaDTO(agendamento));
    }

    /**
     * Somente ROLE: Cliente
     */
    @GetMapping("/todos")
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarAgendamentos(@AuthenticationPrincipal Usuario usuario) {
        List<Agendamento> agendamentos = agendamentoService.buscarAgendamentos(usuario);
        List<AgendamentoResponseDTO> responseDTOs = converteListDTO(agendamentos);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/cliente/{status}")
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarAgendamentosByStatusAndCliente(@AuthenticationPrincipal Usuario usuario, @PathVariable StatusAgendamento status) {
        List<Agendamento> agendamentos = agendamentoService.buscarAgendamentosCliente(usuario, status);
        List<AgendamentoResponseDTO> responseDTOs = converteListDTO(agendamentos);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/profissional/{status}")
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarAgendamentosByStatusAndProfissional(@AuthenticationPrincipal Usuario usuario, @PathVariable StatusAgendamento status) {
        List<Agendamento> agendamentos = agendamentoService.buscarAgendamentosProfissional(usuario, status);
        List<AgendamentoResponseDTO> responseDTOs = converteListDTO(agendamentos);
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Somente ROLE: Profissional
     */
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<AgendamentoResponseDTO> cancelarAgendamento(@PathVariable Long id) {
        Agendamento agendamento = agendamentoService.cancelarAgendamentoById(id);
        return ResponseEntity.ok(converterParaDTO(agendamento));
    }

    private List<AgendamentoResponseDTO> converteListDTO(List<Agendamento> agendamentos) {
        return agendamentos.stream()
                .map(this::converterParaDTO)
                .toList();
    }

    private AgendamentoResponseDTO converterParaDTO(Agendamento agendamento) {
        return mapper.map(agendamento, AgendamentoResponseDTO.class);
    }
}
