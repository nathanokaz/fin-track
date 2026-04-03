package com.projects.fin_track.domain.transacao.repository;

import com.projects.fin_track.domain.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    List<Transacao> findAllByContaOrigemId(Integer id);

}
