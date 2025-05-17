package com.wellscosta.ProSched.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellscosta.ProSched.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
    
}
