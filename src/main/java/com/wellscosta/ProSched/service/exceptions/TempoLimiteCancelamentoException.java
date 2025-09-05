package com.wellscosta.ProSched.service.exceptions;

public class TempoLimiteCancelamentoException extends RuntimeException{
    public TempoLimiteCancelamentoException(String mensagem) {
        super(mensagem);
    }

    public TempoLimiteCancelamentoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public TempoLimiteCancelamentoException() {

    }
}
