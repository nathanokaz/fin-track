package com.projects.fin_track.domain.conta.repository;

import com.projects.fin_track.domain.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
