package com.projects.fin_track.domain.user.dto;

import com.projects.fin_track.domain.user.User;

import java.time.LocalDate;

public record DadosCadastrados(

        Integer id,
        String email,
        String nome,
        LocalDate criadoEm,
        UserRole role) {

    public DadosCadastrados(User novoUser) {
        this(novoUser.getId(), novoUser.getEmail(), novoUser.getNome(), novoUser.getCriadoEm(), novoUser.getRole());
    }

}
