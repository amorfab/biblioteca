package com.meucurso.biblioteca_api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({LivroNaoEncontradoException.class, AutorNaoEncontradoException.class})
    public ResponseEntity<MensagemDeErro> handleNaoEncontrado(RuntimeException ex) {
        return ResponseEntity.status(404).body(new MensagemDeErro(404, ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemDeErro> handleGenerico(Exception ex) {
        return ResponseEntity.status(500).body(new MensagemDeErro(500, "Erro interno do servidor"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidacao(
            MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));
        return ResponseEntity.status(400).body(erros);
    }

}
