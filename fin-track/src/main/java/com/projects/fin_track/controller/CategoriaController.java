package com.projects.fin_track.controller;

import com.projects.fin_track.domain.categoria.dto.CategoriaCadastrada;
import com.projects.fin_track.domain.categoria.dto.DadosCadastroCategoria;
import com.projects.fin_track.domain.categoria.dto.DetalhesCategoria;
import com.projects.fin_track.domain.categoria.dto.EditarDadosCategoria;
import com.projects.fin_track.domain.categoria.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping("/criar")
    public ResponseEntity<CategoriaCadastrada> criarCategoria(@RequestBody @Valid DadosCadastroCategoria dados, UriComponentsBuilder builder) {
        var novaCategoria = categoriaService.criarCategoria(dados);
        var URI = builder.path("/categoria/criar/{id}").buildAndExpand(novaCategoria.id()).toUri();
        return ResponseEntity.created(URI).body(novaCategoria);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DetalhesCategoria>> listarCategorias() {
        var categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok().body(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesCategoria> mostrarCategoriaPorId(@PathVariable Integer id) {
        var categoria = categoriaService.pegarPorId(id);
        return ResponseEntity.ok().body(categoria);
    }

    @PatchMapping("/editar/{id}")
    public ResponseEntity<DetalhesCategoria> editarCategoria(@RequestBody @Valid EditarDadosCategoria dados, @PathVariable Integer id) {
        var categoria = categoriaService.editarCategoria(dados, id);
        return ResponseEntity.ok().body(categoria);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable Integer id) {
        categoriaService.exclusaoLogica(id);
        return ResponseEntity.noContent().build();
    }

}
