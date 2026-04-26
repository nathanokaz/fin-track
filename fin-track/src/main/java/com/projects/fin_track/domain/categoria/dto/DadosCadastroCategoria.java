package com.projects.fin_track.domain.categoria.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCategoria(

        @NotBlank
        String nome) {

}
