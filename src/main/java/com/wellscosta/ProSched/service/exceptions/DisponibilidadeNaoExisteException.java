package com.wellscosta.ProSched.service.exceptions;

public class DisponibilidadeNaoExisteException extends RuntimeException{
    public DisponibilidadeNaoExisteException(String mensagem) {
        super(mensagem);
    }

    public DisponibilidadeNaoExisteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public DisponibilidadeNaoExisteException() {

    }
}
