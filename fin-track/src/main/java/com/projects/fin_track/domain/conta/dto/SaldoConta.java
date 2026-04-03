package com.projects.fin_track.domain.conta.dto;

import com.projects.fin_track.domain.conta.Conta;

import java.math.BigDecimal;

public record SaldoConta(BigDecimal saldo) {

    public SaldoConta(Conta conta) {
        this(conta.getSaldo());
    }

}
