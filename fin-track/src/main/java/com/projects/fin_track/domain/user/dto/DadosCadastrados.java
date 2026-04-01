package com.projects.fin_track.domain.user.dto;

import com.projects.fin_track.domain.user.User;

import java.time.LocalDate;

public record DadosCadastrados(

        String email,
        String nome,
        LocalDate criadoEm) {

    public DadosCadastrados(User novoUser) {
        this(novoUser.getEmail(), novoUser.getNome(), novoUser.getCriadoEm());
    }

}
