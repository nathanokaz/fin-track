package com.projects.fin_track.controller;

import com.projects.fin_track.domain.user.dto.DadosCadastrados;
import com.projects.fin_track.domain.user.dto.DadosCadastro;
import com.projects.fin_track.domain.user.dto.DadosLogin;
import com.projects.fin_track.infra.security.dto.TokenJwt;
import com.projects.fin_track.infra.security.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenJwt> login(@RequestBody @Valid DadosLogin dados) {
        var token = authenticationService.login(dados);
        return ResponseEntity.ok(new TokenJwt(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<DadosCadastrados> cadastrar(@RequestBody @Valid DadosCadastro dados, UriComponentsBuilder builder) {
        var novoUser = authenticationService.cadastrar(dados);
        URI uri = builder
                .path("/auth/cadastrar/{id}")
                .buildAndExpand(novoUser.getId())
                .toUri();
        return ResponseEntity.created(uri).body(new DadosCadastrados(novoUser));
    }


}
