package com.projects.fin_track.infra.exception;

import com.projects.fin_track.infra.exception.dto.ErrorResponse;
import com.projects.fin_track.infra.exception.exceptions.EmailJaCadastradoException;
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
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
