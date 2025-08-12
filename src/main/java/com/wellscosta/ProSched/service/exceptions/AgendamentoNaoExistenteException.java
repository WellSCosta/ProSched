package com.wellscosta.ProSched.service.exceptions;

public class AgendamentoNaoExistenteException extends RuntimeException {
    public AgendamentoNaoExistenteException(String mensagem) {
        super(mensagem);
    }

    public AgendamentoNaoExistenteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public AgendamentoNaoExistenteException() {

    }
}
