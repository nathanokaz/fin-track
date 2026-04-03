package com.projects.fin_track.domain.conta.dto;

import com.projects.fin_track.domain.conta.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaCadastrada(

        Integer id,
        TipoConta tipo,
        BigDecimal saldo,
        LocalDate criadoEm) {

    public ContaCadastrada(Conta conta) {
        this(conta.getId(), conta.getTipoConta(), conta.getSaldo(), conta.getCriadoEm());
    }

}
