package com.wellscosta.ProSched.service;

import com.wellscosta.ProSched.dto.DisponibilidadeRequestDTO;
import com.wellscosta.ProSched.model.Disponibilidade;
import com.wellscosta.ProSched.model.Usuario;
import com.wellscosta.ProSched.repository.DisponibilidadeRepository;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DisponibilidadeService {
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Disponibilidade criarDisponibilidade(DisponibilidadeRequestDTO disponibilidadeDTO, Long usuarioId) {
        Usuario usuario = buscarUsuario(usuarioId);
        validarDispoonibilidade(disponibilidadeDTO, usuario);

        //Salva a nova disponibilidade no banco
        Disponibilidade disponibilidade = Disponibilidade.builder()
                .profissional(usuario)
                .data(disponibilidadeDTO.data())
                .horaInicio(disponibilidadeDTO.horaInicio())
                .horaFim(disponibilidadeDTO.horaFim())
                .build();

        return disponibilidadeRepository.save(disponibilidade);
    }

    private void validarDispoonibilidade(DisponibilidadeRequestDTO disponibilidadeDTO, Usuario usuario) {
        //Valida se a data é futura
        if (!isDataFutura(disponibilidadeDTO.data()))
            throw new IllegalArgumentException("Data não pode ser no passado.");

        //Valida se a hora fim é após a hora inicio
        if (!isHoraFimAposHoraInicio(disponibilidadeDTO.horaInicio(), disponibilidadeDTO.horaFim()))
            throw new IllegalArgumentException("Hora fim não pode ser antes da hora de inicio.");

        //Busca as disponibilidades do usuário
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByProfissionalAndData(usuario, disponibilidadeDTO.data());

        //Verifica se o horário da disponibilidade já está ocupado
        if (isConflitoDisponibilidades(disponibilidadeDTO.horaInicio(), disponibilidadeDTO.horaFim(), disponibilidades))
            throw new IllegalArgumentException("Disponibilidade já existente no mesmo horário.");

    }

    private boolean isHoraFimAposHoraInicio(LocalTime horaInicio, LocalTime horaFim) {
        return horaFim.isAfter(horaInicio);
    }

    private boolean isDataFutura(LocalDate data) {
        return data.isAfter(LocalDate.now());
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }

    private boolean isConflitoDisponibilidades(LocalTime horaInicio, LocalTime horaFim, List<Disponibilidade> disponibilidades) {
        return disponibilidades.stream().anyMatch(d ->
                    d.getHoraFim().isAfter(horaInicio) &&
                    d.getHoraInicio().isBefore(horaFim));
    }
}
