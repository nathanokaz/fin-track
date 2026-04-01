package com.projects.fin_track.controller;

import com.projects.fin_track.domain.user.User;
import com.projects.fin_track.domain.user.dto.DadosCadastrados;
import com.projects.fin_track.domain.user.dto.DadosCadastro;
import com.projects.fin_track.domain.user.dto.DadosLogin;
import com.projects.fin_track.domain.user.dto.UserRole;
import com.projects.fin_track.domain.user.repository.UserRepository;
import com.projects.fin_track.infra.security.dto.TokenJwt;
import com.projects.fin_track.infra.security.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenJwt> login(@RequestBody @Valid DadosLogin dados) {
        var user = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var auth = authenticationManager.authenticate(user);
        var token = tokenService.gerarToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenJwt(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<DadosCadastrados> cadastrar(@RequestBody @Valid DadosCadastro dados, UriComponentsBuilder builder) {
        if (userRepository.findByEmail(dados.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());
        var dataAtual = LocalDate.now();
        User novoUser = User.builder()
                    .email(dados.email())
                    .senha(senhaCriptografada)
                    .nome(dados.nome())
                    .role(UserRole.USER)
                    .criadoEm(dataAtual)
                    .ativo(true)
                    .build();
        userRepository.save(novoUser);
        URI uri = builder.path("/auth/cadastrar/{id}").buildAndExpand(novoUser.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosCadastrados(novoUser));
    }


}
