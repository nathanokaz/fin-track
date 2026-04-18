package com.projects.fin_track.domain.transacao.dto;

import com.projects.fin_track.domain.categoria.Categoria;
import com.projects.fin_track.domain.transacao.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosTransacaoCadastrada(

        Integer id,
        BigDecimal valor,
        TipoTransacao tipo,
        LocalDateTime data,
        String descricao,
        Categoria categoria,
        Integer contaOrigemId,
        Integer contaDestinoId){

    public DadosTransacaoCadastrada(Transacao transacao) {
        this(transacao.getId(), transacao.getValor(), transacao.getTipo(), transacao.getData(),
                transacao.getDescricao(), transacao.getCategoria(),
                transacao.getContaOrigem() != null ? transacao.getContaOrigem().getId() : null,
                transacao.getContaDestino() != null ? transacao.getContaDestino().getId() : null);
    }

}
