package com.projects.fin_track.domain.transacao.repository;

import com.projects.fin_track.domain.conta.Conta;
import com.projects.fin_track.domain.transacao.Transacao;
import com.projects.fin_track.domain.transacao.dto.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    List<Transacao> findAllByContaOrigemIn(List<Conta> contas);

    @Query(value = "SELECT t FROM Transacao t WHERE (t.contaOrigem IN :contas OR t.contaDestino IN :contas)")
    List<Transacao> findAllContas(@Param("contas") List<Conta> contas);

    @Query(value = "SELECT t FROM  Transacao t WHERE t.tipo = :tipo AND (t.contaOrigem IN :contas OR t.contaDestino IN :contas)")
    List<Transacao> findAllByTipoTransacao(@Param("tipo") TipoTransacao tipo,
                                           @Param("contas") List<Conta> contas);
}
