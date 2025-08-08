package com.wellscosta.ProSched.service;

import com.wellscosta.ProSched.dto.AgendamentoDisponibilidadeRequestDTO;
import com.wellscosta.ProSched.model.Agendamento;
import com.wellscosta.ProSched.model.Disponibilidade;
import com.wellscosta.ProSched.model.Usuario;
import com.wellscosta.ProSched.model.enums.StatusAgendamento;
import com.wellscosta.ProSched.repository.AgendamentoRepository;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private DisponibilidadeService disponibilidadeService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cliente solicita agendamento para o profissional
     */
    public Agendamento solicitarAgendamento(AgendamentoDisponibilidadeRequestDTO agendamentoDTO, Long usuarioId) {
        Usuario cliente = buscarUsuario(usuarioId);
        Disponibilidade disponibilidade = verificaDisponibilidade(agendamentoDTO);

        return salvarSolicitacaoBanco(disponibilidade, cliente);
    }

    /**
     * Salvar a solicitação de agendamento no banco de dados
     *
     * Atributo horario recebe o horario inicio da disponibilidade
     * Atributo profissional recebe o profissional da disponibilidade
     */
    private Agendamento salvarSolicitacaoBanco(Disponibilidade disponibilidade, Usuario cliente) {
        Agendamento agendamento = Agendamento.builder()
                .cliente(cliente)
                .dataCriacao(LocalDateTime.now())
                .horario(disponibilidade.getData().atTime(disponibilidade.getHoraInicio()))
                .status(StatusAgendamento.SOLICITADO)
                .profissional(disponibilidade.getProfissional())
                .build();

        return agendamentoRepository.save(agendamento);
    }

    /**
     * Verifica se existe a disponibilidade no banco.
     */
    private Disponibilidade verificaDisponibilidade(AgendamentoDisponibilidadeRequestDTO dto) {
        Disponibilidade disponibilidade = criarDisponibilidade(dto);

        boolean existeDisponibilidade = disponibilidadeService.diponibilidadeExiste(disponibilidade);
        if (!existeDisponibilidade) {
            throw new RuntimeException("Disponibilidade não existente");
        }

        return disponibilidade;
    }

    /**
     * Cria uma instância de disponibilidade a partir do DTO para verificar.
     */
    private Disponibilidade criarDisponibilidade(AgendamentoDisponibilidadeRequestDTO dto) {
        return Disponibilidade.builder()
                .horaFim(dto.horaFim())
                .data(dto.data())
                .horaInicio(dto.horaInicio())
                .profissional(dto.profissional())
                .build();
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }
}
