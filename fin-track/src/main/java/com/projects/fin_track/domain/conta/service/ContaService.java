package com.projects.fin_track.domain.conta.service;

import com.projects.fin_track.domain.conta.Conta;
import com.projects.fin_track.domain.conta.dto.*;
import com.projects.fin_track.domain.conta.repository.ContaRepository;
import com.projects.fin_track.domain.transacao.Transacao;
import com.projects.fin_track.domain.transacao.dto.DetalhesTransacoes;
import com.projects.fin_track.domain.transacao.repository.TransacaoRepository;
import com.projects.fin_track.domain.user.User;
import com.projects.fin_track.domain.user.repository.UserRepository;
import com.projects.fin_track.infra.exception.exceptions.ContaNaoEncontrada;
import com.projects.fin_track.infra.exception.exceptions.HaSaldoEmConta;
import com.projects.fin_track.infra.exception.exceptions.NomeDaContaJaExiste;
import com.projects.fin_track.infra.exception.exceptions.UsuarioNaoEncontrado;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final UserRepository userRepository;
    private final TransacaoRepository transacaoRepository;

    @Transactional
    public ContaCadastrada criarConta(DadosCadastroConta dados) {
        var user = pegarUsuario();
        if (contaRepository.existsByNomeAndUserId(dados.nome(), user.getId())) {
            throw new NomeDaContaJaExiste("Já existe uma conta com esse nome");
        }
        Conta novaConta = Conta.builder()
                .nome(dados.nome())
                .tipoConta(dados.tipo())
                .saldo(dados.saldo())
                .user(user)
                .transacaoSaida(null)
                .transacaoEntrada(null)
                .criadoEm(LocalDate.now())
                .build();
        contaRepository.save(novaConta);
        return new ContaCadastrada(novaConta);
    }

    public List<DetalhesConta> listarContas() {
        var user = pegarUsuario();
        List<Conta> contas = contaRepository.findAllByUserId(user.getId());
        return contas.stream().map(conta -> new DetalhesConta(conta)).toList();
    }

    public DetalhesConta mostrarContaPorId(Integer id) {
        var conta = contaRepository.findByIdAndUserId(id, pegarUsuario().getId())
                .orElseThrow(() -> new ContaNaoEncontrada("Conta não encontrada"));
        return new DetalhesConta(conta);
    }

    @Transactional
    public DetalhesConta editarConta(EditarDadosConta dados, Integer id) {
        var user = pegarUsuario();
        var conta = contaRepository.findByIdAndUserId(id, pegarUsuario().getId())
                .orElseThrow(() -> new ContaNaoEncontrada("Conta não encontrada"));
        if (dados.nome() != null) {
            if (contaRepository.existsByNomeAndUserId(dados.nome(), user.getId())) {
                throw new NomeDaContaJaExiste("Já existe uma conta com esse nome");
            } else {
                conta.setNome(dados.nome());
            }
        }
        if (dados.tipo() != null) {
            conta.setTipoConta(dados.tipo());
        }
        return new DetalhesConta(conta);
    }

    public SaldoConta consultarSaldo(Integer id) {
        var conta = contaRepository.findByIdAndUserId(id, pegarUsuario().getId())
                .orElseThrow(() -> new ContaNaoEncontrada("Conta não encontrada"));
        return new SaldoConta(conta);
    }

    public List<DetalhesTransacoes> listarTransacoes(Integer id) {
        List<Transacao> transacoes = transacaoRepository.findAllByContaOrigemId(id);
        return transacoes.stream().map(transacao -> new DetalhesTransacoes(transacao)).toList();
    }

    @Transactional
    public void exclusaoLogica(Integer id) {
        var conta = contaRepository.findByIdAndUserId(id, pegarUsuario().getId())
                .orElseThrow(() -> new ContaNaoEncontrada("Conta não encontrada"));
        var saldo = conta.getSaldo();
        if (saldo.compareTo(BigDecimal.ZERO) > 0) {
            throw new HaSaldoEmConta("Você ainda tem saldo em conta");
        }
        contaRepository.deleteById(id);
    }

    private User pegarUsuario() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();
        return userRepository.findByEmailUser(email)
                .orElseThrow(() -> new UsuarioNaoEncontrado("Usuário não encontrado"));
    }

}
