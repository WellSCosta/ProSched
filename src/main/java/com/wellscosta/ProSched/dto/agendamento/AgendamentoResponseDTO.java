package com.wellscosta.ProSched.dto.agendamento;

import com.wellscosta.ProSched.dto.UsuarioResponseDTO;
import com.wellscosta.ProSched.model.enums.StatusAgendamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoResponseDTO {
    private Long id;
    private LocalDateTime horario;
    private UsuarioResponseDTO cliente;
    private UsuarioResponseDTO profissional;
    private StatusAgendamento status;
    private LocalDateTime dataCriacao;
}
