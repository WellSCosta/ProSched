package com.wellscosta.ProSched.model;

import java.time.LocalDateTime;

import com.wellscosta.ProSched.model.enums.StatusAgendamento;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Contém os dados de uma reserva entre cliente e profissional
 */

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime horario;

    @ManyToOne
    private Usuario cliente;
    
    @ManyToOne
    private Usuario profissional;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    private LocalDateTime dataCriacao;
}
