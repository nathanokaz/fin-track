package com.projects.fin_track.domain.transacao.dto;

import com.projects.fin_track.domain.categoria.Categoria;
import com.projects.fin_track.domain.transacao.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DadosTransacoes(
        Integer id,
        BigDecimal valor,
        TipoTransacao tipo,
        LocalDateTime data,
        String descricao,
        Integer categoriaId,
        Integer contaOrigemId,
        Integer contaDestinoId
){
    public DadosTransacoes(Transacao transacao) {
        this(transacao.getId(), transacao.getValor(), transacao.getTipo(), transacao.getData(),
                transacao.getDescricao(), transacao.getCategoria() != null ? transacao.getCategoria().getId() : null,
                transacao.getContaOrigem() != null ? transacao.getContaOrigem().getId() : null,
                transacao.getContaDestino() != null ? transacao.getContaDestino().getId() : null);
    }
}
