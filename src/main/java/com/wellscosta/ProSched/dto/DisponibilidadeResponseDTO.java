package com.wellscosta.ProSched.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadeResponseDTO {
    private Long id;
    private UsuarioResponseDTO profissional;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
}
