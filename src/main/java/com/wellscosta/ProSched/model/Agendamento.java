package com.wellscosta.ProSched.model;

import java.time.LocalDateTime;
import com.wellscosta.ProSched.model.enums.StatusAgendamento;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Contém os dados de uma reserva entre cliente e profissional
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Agendamento {

    private Long id;
    private LocalDateTime horario;
    private Usuario cliente;
    private Usuario profissional;
    private StatusAgendamento status;
    private LocalDateTime dataCriacao;
}
