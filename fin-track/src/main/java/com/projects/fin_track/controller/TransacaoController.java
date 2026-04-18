package com.projects.fin_track.controller;


import com.projects.fin_track.domain.transacao.dto.*;
import com.projects.fin_track.domain.transacao.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService  transacaoService;

    @PostMapping("/criar")
    public ResponseEntity<DadosTransacaoCadastrada> criarTransacao(@RequestBody @Valid DadosCadastroTransacao dados, UriComponentsBuilder builder) {
        var transacao = transacaoService.criarTransacao(dados);
        var URI = builder.path("/transacao/criar/{id}").buildAndExpand(transacao.id()).toUri();
        return ResponseEntity.created(URI).body(transacao);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DadosTransacoes>> listarTransacoes() {
        var transacoes = transacaoService.listarTransacoes();
        return ResponseEntity.ok().body(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosTransacoes> mostrarTransacaoPorId(@PathVariable Integer id) {
        var transacao = transacaoService.mostrarPorId(id);
        return ResponseEntity.ok().body(transacao);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<DadosTransacoes>> mostrarPorTipo(@PathVariable TipoTransacao tipo) {
        var transacao = transacaoService.mostrarPorTipo(tipo);
        return ResponseEntity.ok().body(transacao);
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoTransacoes> resumoTransacoes() {
        var transacoes = transacaoService.resumoTransacoes();
        return ResponseEntity.ok().body(transacoes);
    }

}
