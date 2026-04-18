package com.projects.fin_track.infra.exception.exceptions;

public class TransacaoNaoEncontrada extends RuntimeException {

    public TransacaoNaoEncontrada(String message) {
        super(message);
    }

}
