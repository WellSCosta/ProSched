package com.wellscosta.ProSched.controller;

import com.wellscosta.ProSched.dto.DisponibilidadeRequestDTO;
import com.wellscosta.ProSched.dto.DisponibilidadeResponseDTO;
import com.wellscosta.ProSched.infra.security.TokenService;
import com.wellscosta.ProSched.model.Disponibilidade;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import com.wellscosta.ProSched.service.DisponibilidadeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/criar")
    public ResponseEntity<DisponibilidadeResponseDTO> criarDisponibilidade(@RequestBody @Valid DisponibilidadeRequestDTO requestDTO, @RequestHeader("Authorization") String token) {
        Long usuarioId = getUsuarioId(token);

        //Cria nova disponibilidade
        Disponibilidade disponibilidade = disponibilidadeService.registrar(requestDTO, usuarioId);
        DisponibilidadeResponseDTO responseDTO = modelMapper.map(disponibilidade, DisponibilidadeResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    private Long getUsuarioId(String token) {
        String jwt = token.replace("Bearer ", "");

        //Resgata o email do token
        String email = tokenService.validarToken(jwt);
        //Buscar usuário pelo email
        return usuarioRepository.findIdByEmail(email);
    }
}
