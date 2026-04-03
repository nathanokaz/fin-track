package com.projects.fin_track.controller;

import com.projects.fin_track.domain.conta.dto.*;
import com.projects.fin_track.domain.conta.service.ContaService;
import com.projects.fin_track.domain.transacao.dto.DetalhesTransacoes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @PostMapping("/criar")
    public ResponseEntity<ContaCadastrada> criarConta(@RequestBody @Valid DadosCadastroConta dados, UriComponentsBuilder builder) {
        var novaConta = contaService.criarConta(dados);
        URI uri = builder.path("/conta/criar/{id}").buildAndExpand(novaConta.id()).toUri();
        return ResponseEntity.created(uri).body(novaConta);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DetalhesConta>> listarContas() {
        var contas = contaService.listarContas();
        return ResponseEntity.ok().body(contas);
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesConta> mostrarContaPorId(@PathVariable Integer id) {
        var conta = contaService.mostrarContaPorId(id);
        return ResponseEntity.ok().body(conta);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<DetalhesConta> editarConta(@RequestBody @Valid EditarDadosConta dados, @PathVariable Integer id)  {
        var conta = contaService.editarConta(dados, id);
        return ResponseEntity.ok().body(conta);
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<SaldoConta> consultarSaldoPorId(@PathVariable Integer id) {
        var conta = contaService.consultarSaldo(id);
        return ResponseEntity.ok().body(conta);
    }

    @GetMapping("/transacoes/{id}")
    public ResponseEntity<List<DetalhesTransacoes>> listarTransacoes(@PathVariable Integer id) {
        var transacoes = contaService.listarTransacoes(id);
        return ResponseEntity.ok().body(transacoes);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarConta(@PathVariable Integer id) {
        contaService.exclusaoLogica(id);
        return ResponseEntity.noContent().build();
    }

}
