package com.projects.fin_track.domain.categoria.service;

import com.projects.fin_track.domain.categoria.Categoria;
import com.projects.fin_track.domain.categoria.dto.CategoriaCadastrada;
import com.projects.fin_track.domain.categoria.dto.DadosCadastroCategoria;
import com.projects.fin_track.domain.categoria.dto.DetalhesCategoria;
import com.projects.fin_track.domain.categoria.dto.EditarDadosCategoria;
import com.projects.fin_track.domain.categoria.repository.CategoriaRepository;
import com.projects.fin_track.domain.user.User;
import com.projects.fin_track.domain.user.repository.UserRepository;
import com.projects.fin_track.infra.exception.exceptions.CategoriaNaoEncontrada;
import com.projects.fin_track.infra.exception.exceptions.NomeDaCategoriaJaExiste;
import com.projects.fin_track.infra.exception.exceptions.UsuarioNaoEncontrado;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UserRepository userRepository;

    @Transactional
    public CategoriaCadastrada criarCategoria(DadosCadastroCategoria dados) {
        if (categoriaRepository.existsByNomeAndUserId(dados.nome(), pegarUser().getId())) {
            throw new NomeDaCategoriaJaExiste("Você já tem uma categoria com esse nome");
        }
        Categoria novaCategoria = Categoria.builder()
                .nome(dados.nome())
                .user(pegarUser())
                .build();
        categoriaRepository.save(novaCategoria);
        return new CategoriaCadastrada(novaCategoria);
    }

    public List<DetalhesCategoria> listarCategorias() {
        var user = pegarUser();
        var categorias = categoriaRepository.findAllByUserId(user.getId());
        return categorias.stream().map(categoria -> new DetalhesCategoria(categoria)).toList();
    }

    public DetalhesCategoria pegarPorId(Integer id) {
        var user = pegarUser();
        var categoria = categoriaRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CategoriaNaoEncontrada("Categoria não encontrada"));
        return new DetalhesCategoria(categoria);
    }

    @Transactional
    public DetalhesCategoria editarCategoria(EditarDadosCategoria dados, Integer id) {
        var user = pegarUser();
        var categoria = categoriaRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CategoriaNaoEncontrada("Categoria não encontrada"));
        if (dados.nome() != null) {
            if (categoriaRepository.existsByNomeAndUserId(dados.nome(), user.getId())) {
                throw new NomeDaCategoriaJaExiste("Já existe uma categoria com esse nome");
            } else {
                categoria.setNome(dados.nome());
            }
        }
        return new DetalhesCategoria(categoria);
    }

    @Transactional
    public void exclusaoLogica(Integer id) {
        var user = pegarUser();
        categoriaRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CategoriaNaoEncontrada("Categoria não encontrda"));
        categoriaRepository.deleteById(id);
    }

    private User pegarUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();
        return userRepository.findByEmailUser(email)
                .orElseThrow(() -> new UsuarioNaoEncontrado("Usuário não encontrado"));
    }

}
