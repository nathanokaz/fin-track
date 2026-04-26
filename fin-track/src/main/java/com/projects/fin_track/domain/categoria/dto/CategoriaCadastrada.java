package com.projects.fin_track.domain.categoria.dto;

import com.projects.fin_track.domain.categoria.Categoria;

public record CategoriaCadastrada(

        Integer id,
        String nome

) {

    public CategoriaCadastrada(Categoria novaCategoria) {
        this(novaCategoria.getId(), novaCategoria.getNome());
    }

}
