package com.wellscosta.ProSched.dto;

import com.wellscosta.ProSched.model.Usuario;

public record AgendamentoRequestDTO(AgendamentoDisponibilidadeRequestDTO disponibilidadeDTO, Usuario cliente) {
}
