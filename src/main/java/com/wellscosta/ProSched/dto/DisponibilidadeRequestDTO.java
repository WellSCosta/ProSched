package com.wellscosta.ProSched.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilidadeRequestDTO(LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
}
