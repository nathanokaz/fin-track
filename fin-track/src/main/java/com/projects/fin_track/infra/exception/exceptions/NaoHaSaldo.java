package com.projects.fin_track.infra.exception.exceptions;

public class NaoHaSaldo extends RuntimeException {

    public NaoHaSaldo(String message) {
        super(message);
    }
}
