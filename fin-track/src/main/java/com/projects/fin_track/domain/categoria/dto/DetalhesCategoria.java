package com.projects.fin_track.domain.categoria.dto;

import com.projects.fin_track.domain.categoria.Categoria;

public record DetalhesCategoria(

        Integer id,
        String nome) {

    public DetalhesCategoria(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }

}
