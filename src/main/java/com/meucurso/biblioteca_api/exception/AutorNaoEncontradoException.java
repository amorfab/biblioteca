package com.meucurso.biblioteca_api.exception;

public class AutorNaoEncontradoException extends RuntimeException {
    public AutorNaoEncontradoException(Long id) {
        super("Autor com id " + id + " não encontrado");
    }
}
