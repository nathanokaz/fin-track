package com.projects.fin_track.domain.conta.repository;

import com.projects.fin_track.domain.conta.Conta;
import com.projects.fin_track.domain.transacao.Transacao;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

    boolean existsByNomeAndUserId(String nome, Integer id);

    List<Conta> findAllByUserId(Integer id);

    Optional<Conta> findByIdAndUserId(Integer id, Integer userId);

}
