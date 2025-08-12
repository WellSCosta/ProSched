package com.wellscosta.ProSched.dto.agendamento;

import com.wellscosta.ProSched.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoDisponibilidadeRequestDTO(LocalDate data, LocalTime horaInicio, LocalTime horaFim, Usuario profissional) {
}
