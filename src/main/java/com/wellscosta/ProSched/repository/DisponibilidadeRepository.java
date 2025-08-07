package com.wellscosta.ProSched.repository;

import com.wellscosta.ProSched.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wellscosta.ProSched.model.Disponibilidade;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long>{
    List<Disponibilidade> findByProfissionalAndData(Usuario profissional, LocalDate data);
}
