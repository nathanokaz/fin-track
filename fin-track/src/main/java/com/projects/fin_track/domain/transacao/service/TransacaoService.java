package com.projects.fin_track.domain.transacao.service;

import com.projects.fin_track.domain.categoria.repository.CategoriaRepository;
import com.projects.fin_track.domain.conta.repository.ContaRepository;
import com.projects.fin_track.domain.transacao.Transacao;
import com.projects.fin_track.domain.transacao.dto.*;
import com.projects.fin_track.domain.transacao.repository.TransacaoRepository;
import com.projects.fin_track.domain.user.User;
import com.projects.fin_track.domain.user.repository.UserRepository;
import com.projects.fin_track.infra.exception.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UserRepository userRepository;
    private final ContaRepository contaRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public DadosTransacaoCadastrada criarTransacao(DadosCadastroTransacao dados) {
        Transacao transacao = null;
        var categoria = categoriaRepository.findByIdAndUserId(dados.categoriaId(), pegarUsuario().getId())
                .orElseThrow(() -> new ContaNaoEncontrada("Categoria não encontrada"));
        if (dados.tipo() == TipoTransacao.TRANSFERENCIA) {
            if (dados.contaOrigemId() == null || dados.contaDestinoId() == null) {
                throw new ContaNaoEncontrada("Conta origem ou destino não informada");
            }
            var valorTransacao = dados.valor();
            if (valorTransacao == null || valorTransacao.compareTo(BigDecimal.ZERO) <= 0) {
                throw new TransferenciaProibida("Valor de transação inválido");
            }
            var contaOrigem = contaRepository.findById(dados.contaOrigemId())
                    .orElseThrow(() -> new ContaNaoEncontrada("Conta origem não encontrada"));
            var contaDestino = contaRepository.findById(dados.contaDestinoId())
                    .orElseThrow(() -> new ContaNaoEncontrada("Conta destino não encontrada"));

            if (!contaOrigem.getUser().getId().equals(pegarUsuario().getId())) {
                throw new ContaNaoEncontrada("Conta origem não encontrada");
            }
            if (contaOrigem.getId().equals(contaDestino.getId())) {
                throw new TransferenciaProibida("Você não pode tranferir para si mesmo");
            }
            if (contaOrigem.getSaldo().compareTo(valorTransacao) < 0) {
                throw new NaoHaSaldo("Não há saldo suficiente");
            }
            contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valorTransacao));
            contaDestino.setSaldo(contaDestino.getSaldo().add(valorTransacao));
            transacao = Transacao.builder()
                    .valor(valorTransacao)
                    .tipo(dados.tipo())
                    .data(LocalDateTime.now())
                    .descricao(dados.descricao())
                    .contaOrigem(contaOrigem)
                    .contaDestino(contaDestino)
                    .categoria(categoria)
                    .build();

            transacaoRepository.save(transacao);
        } else if (dados.tipo() == TipoTransacao.DEPOSITO) {
            if (dados.contaDestinoId() == null) {
                throw new ContaNaoEncontrada("Conta destino não informada");
            }
            var valorTransacao = dados.valor();
            if (valorTransacao == null || valorTransacao.compareTo(BigDecimal.ZERO) <= 0) {
                throw new TransferenciaProibida("Valor de transação inválido");
            }
            var contaDestino = contaRepository.findById(dados.contaDestinoId())
                    .orElseThrow(() -> new ContaNaoEncontrada("Conta destino não encontrada"));
            contaDestino.setSaldo(contaDestino.getSaldo().add(valorTransacao));
            transacao = Transacao.builder()
                    .valor(valorTransacao)
                    .tipo(dados.tipo())
                    .data(LocalDateTime.now())
                    .descricao(dados.descricao())
                    .contaOrigem(null)
                    .contaDestino(contaDestino)
                    .categoria(categoria)
                    .build();
            transacaoRepository.save(transacao);
        } else if (dados.tipo() == TipoTransacao.SAQUE) {
            if (dados.contaOrigemId() == null) {
                throw new ContaNaoEncontrada("Conta origem não informada");
            }
            var valorTransacao = dados.valor();
            if (valorTransacao == null || valorTransacao.compareTo(BigDecimal.ZERO) <= 0) {
                throw new TransferenciaProibida("Valor de transação inválido");
            }
            var contaOrigem = contaRepository.findById(dados.contaOrigemId())
                    .orElseThrow(() -> new ContaNaoEncontrada("Conta origem não encontrada"));
            if (contaOrigem.getSaldo().compareTo(valorTransacao) < 0) {
                throw new NaoHaSaldo("Não há saldo suficiente");
            }
            contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valorTransacao));
            transacao = Transacao.builder()
                    .valor(valorTransacao)
                    .tipo(dados.tipo())
                    .data(LocalDateTime.now())
                    .descricao(dados.descricao())
                    .contaOrigem(contaOrigem)
                    .contaDestino(null)
                    .categoria(categoria)
                    .build();
            transacaoRepository.save(transacao);
        } else {
            throw new TransacaoNaoEncontrada("Tipo de transação inválido");
        }
        return new DadosTransacaoCadastrada(transacao);
    }

    public List<DadosTransacoes> listarTransacoes() {
        var contas = pegarUsuario().getContas();
        var transacoes = transacaoRepository.findAllByContaOrigemIn(contas);
        return transacoes.stream().map(transacao -> new DadosTransacoes(transacao)).toList();
    }

    public DadosTransacoes mostrarPorId(Integer id) {
        var contas = pegarUsuario().getContas();
        var transacoes = transacaoRepository.findAllContas(contas);
        for (Transacao t : transacoes) {
            if (t.getId().equals(id)) {
                return new DadosTransacoes(t);
            }
        }
        throw new TransacaoNaoEncontrada("Transação não encontrada");
    }

    public List<DadosTransacoes> mostrarPorTipo(TipoTransacao tipo) {
        var contas = pegarUsuario().getContas();
        var transacoes = transacaoRepository.findAllByTipoTransacao(tipo, contas);
        return transacoes.stream().map(transacao -> new DadosTransacoes(transacao)).toList();
    }

    public ResumoTransacoes resumoTransacoes() {
        var contas = pegarUsuario().getContas();
        var transacoesTransferencia = transacaoRepository.findAllByTipoTransacao(TipoTransacao.TRANSFERENCIA, contas);
        var transacoesSaque = transacaoRepository.findAllByTipoTransacao(TipoTransacao.SAQUE, contas);
        var transacoesDeposito = transacaoRepository.findAllByTipoTransacao(TipoTransacao.DEPOSITO, contas);
        var listaValorTransferencias = transacoesTransferencia.stream().map(transacao -> transacao.getValor()).toList();
        var listaValorSaque = transacoesSaque.stream().map(transacao -> transacao.getValor()).toList();
        var listaValorDeposito = transacoesDeposito.stream().map(transacao -> transacao.getValor()).toList();
        var valorTransferencias = BigDecimal.ZERO;
        var valorSaque = BigDecimal.ZERO;
        var valorDeposito = BigDecimal.ZERO;
        for (BigDecimal valor : listaValorTransferencias) {
            valorTransferencias = valorTransferencias.add(valor);
        }
        for (BigDecimal valor : listaValorSaque) {
            valorSaque = valorSaque.add(valor);
        }
        for (BigDecimal valor : listaValorDeposito) {
            valorDeposito = valorDeposito.add(valor);
        }
        return new ResumoTransacoes(valorTransferencias, valorSaque, valorDeposito);
    }

    private User pegarUsuario() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();
        return userRepository.findByEmailUser(email)
                .orElseThrow(() -> new UsuarioNaoEncontrado("Usuário não encontrado"));
    }
}
