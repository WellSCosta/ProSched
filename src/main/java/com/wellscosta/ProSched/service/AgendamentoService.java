package com.wellscosta.ProSched.service;

import com.wellscosta.ProSched.dto.agendamento.AgendamentoConfirmarRequestDTO;
import com.wellscosta.ProSched.dto.agendamento.AgendamentoDisponibilidadeRequestDTO;
import com.wellscosta.ProSched.model.Agendamento;
import com.wellscosta.ProSched.model.Disponibilidade;
import com.wellscosta.ProSched.model.Usuario;
import com.wellscosta.ProSched.model.enums.StatusAgendamento;
import com.wellscosta.ProSched.repository.AgendamentoRepository;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import com.wellscosta.ProSched.service.exceptions.AgendamentoNaoExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Agendamento solicitarAgendamento(AgendamentoDisponibilidadeRequestDTO agendamentoDTO, Usuario cliente) {
        Disponibilidade disponibilidade = verificaDisponibilidade(agendamentoDTO);

        return salvarSolicitacaoBanco(disponibilidade, cliente);
    }

    /**
     * Set o status do agendamento para confirmado e salva no banco.
     * Set o status dos agendamentos restantes do mesmo horário como cancelados e salva no banco.
     * TODO verificações para os status cancelado e confirmado
     */
    public Agendamento confirmarAgendamento(AgendamentoConfirmarRequestDTO dto) {
        Agendamento agendamento = buscarPorId(dto.id()).orElseThrow(AgendamentoNaoExistenteException::new);
        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        agendamento = agendamentoRepository.save(agendamento);
        cancelarAgendamentosSolicitadoPorHorario(agendamento.getHorario(), agendamento.getProfissional());
        return agendamento;
    }

    private Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    private void cancelarAgendamentosSolicitadoPorHorario(LocalDateTime horario, Usuario profissional) {
        List<Agendamento> agendamentosSolicitados = agendamentoRepository
                .findByProfissionalAndHorarioAndStatus(profissional, horario, StatusAgendamento.SOLICITADO);

        agendamentosSolicitados.forEach(agendamentos -> agendamentos.setStatus(StatusAgendamento.CANCELADO));
        agendamentoRepository.saveAll(agendamentosSolicitados);
    }

    /**
     * Salvar a solicitação de agendamento no banco de dados
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
}
