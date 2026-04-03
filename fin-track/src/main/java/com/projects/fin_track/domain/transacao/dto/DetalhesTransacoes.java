package com.projects.fin_track.domain.transacao.dto;

import com.projects.fin_track.domain.categoria.Categoria;
import com.projects.fin_track.domain.conta.Conta;
import com.projects.fin_track.domain.transacao.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DetalhesTransacoes(

        Integer id,
        BigDecimal valor,
        TipoTransacao tipo,
        LocalDateTime data,
        String descricao,
        Categoria categoria,
        Conta contaOrigem,
        Conta contaDestino) {

    public DetalhesTransacoes(Transacao transacao) {
        this(transacao.getId(), transacao.getValor(), transacao.getTipo(),
                transacao.getData(), transacao.getDescricao(), transacao.getCategoria(),
                transacao.getContaOrigem(), transacao.getContaDestino());
    }

}
