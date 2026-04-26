package com.projects.fin_track.domain.categoria.repository;

import com.projects.fin_track.domain.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findAllByUserId(Integer id);

    Optional<Categoria> findByIdAndUserId(Integer id, Integer userId);

    boolean existsByNomeAndUserId(String nome, Integer id);
}
