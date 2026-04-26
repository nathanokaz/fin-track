package com.projects.fin_track.infra.exception;

import com.projects.fin_track.infra.exception.dto.ErrorResponse;
import com.projects.fin_track.infra.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> handleEmailJaCadastrado(EmailJaCadastradoException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UsuarioNaoEncontrado.class)
    public ResponseEntity<ErrorResponse> handleUsuarioNaoEncontrado(UsuarioNaoEncontrado exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NomeDaContaJaExiste.class)
    public ResponseEntity<ErrorResponse> handleNomeDaContaJaExiste(NomeDaContaJaExiste exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ContaNaoEncontrada.class)
    public ResponseEntity<ErrorResponse> handleContaNaoEncontrada(ContaNaoEncontrada exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(HaSaldoEmConta.class)
    public ResponseEntity<ErrorResponse> handleHaSaldoEmConta(HaSaldoEmConta exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(NaoHaSaldo.class)
    public ResponseEntity<ErrorResponse> handleNaoHaSaldo(NaoHaSaldo exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(TransferenciaProibida.class)
    public ResponseEntity<ErrorResponse> handleTransferenciaProibida(TransferenciaProibida exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(TransacaoNaoEncontrada.class)
    public ResponseEntity<ErrorResponse> handleTransacaoNaoEncontrada(TransacaoNaoEncontrada exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ValorTransacaoInvalido.class)
    public ResponseEntity<ErrorResponse> handleValorTransacaoInvalido(ValorTransacaoInvalido exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CategoriaNaoEncontrada.class)
    public ResponseEntity<ErrorResponse> handleCategoriaNaoEncontrada(CategoriaNaoEncontrada exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NomeDaCategoriaJaExiste.class)
    public ResponseEntity<ErrorResponse> handleNomeDaCategoriaJaExiste(NomeDaCategoriaJaExiste exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensagem(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

}
