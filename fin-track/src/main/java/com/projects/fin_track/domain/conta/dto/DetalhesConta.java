package com.projects.fin_track.domain.conta.dto;

import com.projects.fin_track.domain.conta.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DetalhesConta(
        Integer id,
        BigDecimal saldo,
        String nome,
        TipoConta tipo,
        LocalDate criadoEm) {

    public DetalhesConta(Conta conta) {
        this(conta.getId(), conta.getSaldo(), conta.getNome(), conta.getTipoConta(), conta.getCriadoEm());
    }

}
