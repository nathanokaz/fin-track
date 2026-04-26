package com.projects.fin_track.domain.conta.repository;

import com.projects.fin_track.domain.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

    boolean existsByNomeAndUserId(String nome, Integer id);

    List<Conta> findAllByUserId(Integer id);

    Optional<Conta> findByIdAndUserId(Integer id, Integer userId);

}
