package com.projects.fin_track.domain.transacao.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroTransacao(

        @NotNull
        BigDecimal valor,

        @NotNull
        TipoTransacao tipo,

        String descricao,

        Integer contaDestinoId,

        Integer contaOrigemId) {
}
