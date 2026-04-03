package com.projects.fin_track.infra.security.service;

import com.projects.fin_track.domain.user.User;
import com.projects.fin_track.domain.user.dto.DadosCadastro;
import com.projects.fin_track.domain.user.dto.DadosLogin;
import com.projects.fin_track.domain.user.dto.UserRole;
import com.projects.fin_track.domain.user.repository.UserRepository;
import com.projects.fin_track.infra.exception.exceptions.EmailJaCadastradoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public String login(DadosLogin dados) {
        var user = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var auth = authenticationManager.authenticate(user);
        return tokenService.gerarToken((User) auth.getPrincipal());
    }

    @Transactional
    public User cadastrar(DadosCadastro dados) {
        if (userRepository.findByEmail(dados.email()) != null) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());
        var dataAtual = LocalDate.now();
        User novoUser = User.builder()
                .email(dados.email())
                .senha(senhaCriptografada)
                .nome(dados.nome())
                .role(UserRole.USER)
                .criadoEm(dataAtual)
                .build();
        userRepository.save(novoUser);
        return novoUser;
    }

}
