package com.projects.fin_track.infra.exception.exceptions;

public class NomeDaCategoriaJaExiste extends RuntimeException {

    public NomeDaCategoriaJaExiste(String message) {
        super(message);
    }

}
