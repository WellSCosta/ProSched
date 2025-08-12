package com.wellscosta.ProSched.repository;

import com.wellscosta.ProSched.model.Agendamento;
import com.wellscosta.ProSched.model.Usuario;
import com.wellscosta.ProSched.model.enums.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
    List<Agendamento> findByProfissionalAndHorarioAndStatus(Usuario profissional, LocalDateTime horario, StatusAgendamento status);
}
