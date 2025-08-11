package com.wellscosta.ProSched.service;

import com.wellscosta.ProSched.dto.DisponibilidadeRequestDTO;
import com.wellscosta.ProSched.model.Disponibilidade;
import com.wellscosta.ProSched.model.Usuario;
import com.wellscosta.ProSched.repository.DisponibilidadeRepository;
import com.wellscosta.ProSched.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
    /**
     * Busca o usuario pelo ID
     * Cria uma instância disponibilidade para validar
     * Valida a disponibilidade
     * Registra a disponibilidade no banco
     * TODO Criar validação se usuario não é nulo, disponibilidade não pode ser criadas com usuário nulo!
     * TODO Verificar por que é possivel registrar nova disponibilidade sem fazer login, apenas com o registro de usuario.
     *
     * @return disponibilidade salva no banco
     */
    public Disponibilidade registrar(DisponibilidadeRequestDTO disponibilidadeDTO, Usuario profissional) {
        Disponibilidade disponibilidade = criarDisponibilidade(disponibilidadeDTO, profissional);
        validarDisponibilidade(disponibilidade, profissional);

        return salvarNoBanco(disponibilidade);
    }

    /**
     * Verifica no banco se a disponibilidade existe.
     */
    public boolean diponibilidadeExiste(Disponibilidade disponibilidade) {
        Example<Disponibilidade> queryExample = Example.of(disponibilidade);
        return disponibilidadeRepository.exists(queryExample);
    }

    public List<Disponibilidade> buscarPorProfissionalEData(LocalDate data, Usuario profissional) {
        return disponibilidadeRepository.findByProfissionalAndData(profissional, data);
    }

    private Disponibilidade salvarNoBanco(Disponibilidade disponibilidade) {
        return disponibilidadeRepository.save(disponibilidade);
    }

    private Disponibilidade criarDisponibilidade(DisponibilidadeRequestDTO disponibilidadeDTO, Usuario profissional) {
        return Disponibilidade.builder()
                .profissional(profissional)
                .data(disponibilidadeDTO.data())
                .horaInicio(disponibilidadeDTO.horaInicio())
                .horaFim(disponibilidadeDTO.horaFim())
                .build();
    }

    /**
     * Valida se a data é futura
     * Valida se o horário fim é após o horário início
     * Valida se há conflitos de horário
     */
    private void validarDisponibilidade(Disponibilidade disponibilidade, Usuario profissional) {
        validarDataFutura(disponibilidade.getData());
        validarHorarioInicioFim(disponibilidade.getHoraInicio(), disponibilidade.getHoraFim());
        validarConflitoDeHorarios(disponibilidade, profissional);
    }

    private void validarHorarioInicioFim(LocalTime horaInicio, LocalTime horaFim) {
        if (!horaFim.isAfter(horaInicio)) {
            throw new IllegalArgumentException("Hora fim não pode ser antes da hora de inicio.");
        }
    }

    private void validarDataFutura(LocalDate data) {
        if (!data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data não pode ser no passado.");
        }
    }

    /**
     * Busca as disponibilidades do profissional no banco e verifica se há conflitos.
     */
    private void validarConflitoDeHorarios(Disponibilidade novaDisponibilidade, Usuario profissional) {
        List<Disponibilidade> existentes = buscarPorProfissionalEData(novaDisponibilidade.getData(), profissional);

        boolean haConflito = existeConflito(novaDisponibilidade, existentes);

        if (haConflito) {
            throw new IllegalArgumentException("Disponibilidade já existente no mesmo horário.");
        }
    }

    private boolean existeConflito(Disponibilidade nova, List<Disponibilidade> existentes) {
        return existentes.stream().anyMatch(existente ->
                existente.getHoraFim().isAfter(nova.getHoraInicio()) &&
                        existente.getHoraInicio().isBefore(nova.getHoraFim())
        );
    }
}
