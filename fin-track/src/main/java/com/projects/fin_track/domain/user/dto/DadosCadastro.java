package com.projects.fin_track.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastro(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String senha,

        @NotBlank
        String nome) {
}
