package com.wellscosta.ProSched.service.exceptions;

public class StatusAgendamentoInvalidoException extends RuntimeException{
    public StatusAgendamentoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public StatusAgendamentoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public StatusAgendamentoInvalidoException() {

    }
}
